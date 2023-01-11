#!/usr/bin/env bash

declare -a ARGS=("${@}")
ARG1=$1

# this whole next part is because there's no readlink -f in Darwin
function readlinkf() {
  FINDFILE="$1"
  FILE="${FINDFILE}"
  PREVFILE=""
  C=0
  MAX=100 # just in case we end up in a loop
  FOUND=0
  while [ "${C}" -lt "${MAX}" -a "${FILE}" != "${PREVFILE}" -a "${FOUND}" -ne 1 ]; do
    PREVFILE="${FILE}"
    FILE="$(readlink "${FILE}")"
    if [ -z "${FILE}" ]; then
      # the readlink is empty means we've arrived at the script, let's canonicalize with pwd
      FILE="$(cd "$(dirname "${PREVFILE}")" &> /dev/null && pwd -P)"/"$(basename "${PREVFILE}")"
      FOUND=1
    elif [ "${FILE#/}" = "${FILE}" ]; then
      # FILE is not an absolute path link, we need to add the relative path to the previous dir
      FILE="$(dirname "${PREVFILE}")/${FILE}"
    fi
    C=$((C+1))
  done
  if [ "${FOUND}" -ne 1 ]; then
    echo "Could not determine path to actual file '$(basename "${FINDFILE}")'" >&2
    exit 1
  fi
  echo "${FILE}"
}

ISMACOS=0
if [ "$( uname -s )" = "Darwin" ]; then
  ISMACOS=1
fi

declare -a JVMARGS=()

# set vars for being inside the macos App Bundle
if [ "${ISMACOS}" = 1 ]; then
# MACOS ONLY
  DIR="$(dirname "$(readlinkf "$0")")"
  APP="${DIR%.app/Contents/*}".app
  if [ "${APP}" = "${APP%.app}" ]; then
    echo "Could not find Jalview.app" >&2
    exit 2
  fi
  APPDIR="${APP}/Contents/Resources/app"
  JAVA="${APPDIR}/jre/Contents/Home/bin/java"
  JVMARGS=( "${JVMARGS[@]}" "-Xdock:icon=${APPDIR}/resource/jalview_logo.png" )
else
# NOT MACOS
  DIR="$(dirname "$(readlink -f "$0")")"
  APPDIR="${DIR%/bin}"
  JAVA="${APPDIR}/jre/bin/java"
fi

SYSJAVA=java
GETDOWNTXT="${APPDIR}/getdown.txt"

CLASSPATH=""
# save an array of JAR paths in case we're in WSL (see later)
declare -a JARPATHS=()
if [ -e "${GETDOWNTXT}" ]; then
  # always check grep and sed regexes on macos -- they're not the same
  for JAR in $(grep -e '^code\s*=\s*' "${GETDOWNTXT}" | sed -e 's/^code\s*=\s*//;'); do
    [ -n "${CLASSPATH}" ] && CLASSPATH="${CLASSPATH}:"
    CLASSPATH="${CLASSPATH}${APPDIR}/${JAR}"
    JARPATHS=( "${JARPATHS[@]}" "${APPDIR}/${JAR}" )
  done
else
  echo "Cannot find getdown.txt" >&2
  exit 3
fi

# WINDOWS ONLY (Cygwin or WSL)
# change paths for Cygwin or Windows Subsystem for Linux (WSL)
if [ "${ISMACOS}" != 1 ]; then # macos doesn't like uname -o, best to avoid
  if [ "$(uname -o)" = "Cygwin" ]; then
  # CYGWIN
    echo "When using relative paths in args within Cygwin, please start with './' or '../'" >&2
    CLASSPATH=$(cygpath -pw "${CLASSPATH}")
    # now for some arg paths fun. only translating paths starting with './', '../', '/' or '~'
    ARGS=()
    for ARG in "${@}"; do
      if [ "${ARG}" != "${ARG#@(/|./|../|~)}" ]; then
        ARGS=( "${ARGS[@]}" "$(cygpath -aw "${ARG}")" )
      else
        ARGS=( "${ARGS[@]}" "${ARG}" )
      fi
    done
  elif uname -r | grep Microsoft >/dev/null; then
  # WSL
    echo "When using relative paths in args within WSL, please start with './' or '../'" >&2
    CLASSPATH=""
    for JARPATH in "${JARPATHS[@]}"; do
      [ -n "${CLASSPATH}" ] && CLASSPATH="${CLASSPATH};"
      CLASSPATH="${CLASSPATH}$(wslpath -aw "${JARPATH}")"
    done
    ARGS=()
    for ARG in "${@}"; do
      if [ "${ARG}" != "${ARG#@(/|./|../|~)}" ]; then
        # annoyingly wslpath does not work if the file doesn't exist!
        ARGBASENAME="$(basename "${ARG}")"
        ARGDIRNAME="$(dirname "${ARG}")"
        ARGS=( "${ARGS[@]}" "$(wslpath -aw "${ARGDIRNAME}")\\${ARGBASENAME}" )
      else
        ARGS=( "${ARGS[@]}" "${ARG}" )
      fi
    done
    JAVA="${JAVA}.exe"
    SYSJAVA="java.exe"
  fi
fi

# Is there a bundled Java?  If not just try one in the PATH (do need .exe in WSL)
if [ \! -e "${JAVA}" ]; then
  JAVA=$SYSJAVA
  echo "Cannot find bundled java, using system ${JAVA} and hoping for the best!" >&2
fi

# check to see if $1 is set and is not start of other cli args
OPEN=""
if [ -n "${ARG1}" -a "${ARG1}" = "${ARG1#-}" -a "${ARG1}" != "open" ]; then
 # first argument exists and does not start with a "-" and is not "open"
 OPEN="-open"
fi

# don't quote $OPEN (don't want it accidentally mistaken as an empty string arg!)
"${JAVA}" "${JVMARGS[@]}" -cp "${CLASSPATH}" jalview.bin.Launcher ${OPEN} "${ARGS[@]}"
