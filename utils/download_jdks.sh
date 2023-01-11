#!/usr/bin/env bash

# see https://api.adoptopenjdk.net/swagger-ui/#/Binary/get_v3_binary_latest__feature_version___release_type___os___arch___image_type___jvm_impl___heap_size___vendor_


### bs 2020-01-22
### This is a script to download and update the JREs used in the windows, mac (and maybe linux) installations, and update channel
### It creates a structure with
### ./jre-VERSION-OS-ARCH/jre/...
### as used by getdown
### and
### ./tgz/jre-VERSION-OS-ARCH.tgz
### which is an archive of the _contents_ of ./jre-VERSION-OS-ARCH/jre/ and used by install4j for the installer
### bs 2021-10-26
### Edited to use adoptium domain to gain access to Java 17 (LTS) versions.

BASE=https://api.adoptium.net/v3/binary/latest
RELEASE_TYPE=ga
JVM_IMPL=hotspot
HEAP_SIZE=normal
VENDOR=eclipse
IMAGE_TYPE=jdk

RM=/bin/rm

# unzip-strip from https://superuser.com/questions/518347/equivalent-to-tars-strip-components-1-in-unzip
unzip-strip() (
  local zip=$1
  local dest=${2:-.}
  local temp=$(mktemp -d) && unzip -qq -d "$temp" "$zip" && mkdir -p "$dest" &&
  shopt -s dotglob && local f=("$temp"/*) &&
  if (( ${#f[@]} == 1 )) && [[ -d "${f[0]}" ]] ; then
    mv "$temp"/*/* "$dest"
  else
    mv "$temp"/* "$dest"
  fi && rmdir "$temp"/* "$temp"
)

for FEATURE_VERSION in 8 11 17; do
  for OS_ARCH in mac:x64 mac:aarch64 windows:x64 linux:x64 linux:arm linux:aarch64; do
    OS=${OS_ARCH%:*}
    ARCH=${OS_ARCH#*:}
    NAME="${IMAGE_TYPE}-${FEATURE_VERSION}-${OS}-${ARCH}"
    TARFILE="${NAME}.tgz"
    echo "* Downloading ${TARFILE}"
    URL="${BASE}/${FEATURE_VERSION}/${RELEASE_TYPE}/${OS}/${ARCH}/${IMAGE_TYPE}/${JVM_IMPL}/${HEAP_SIZE}/${VENDOR}"
    wget -q -O "${TARFILE}" "${URL}"
    if [ "$?" != 0 ]; then
      echo "- No ${IMAGE_TYPE}-${FEATURE_VERSION} download for ${OS}-${ARCH} '${URL}'"
      $RM -f "${TARFILE}"
      continue;
    fi
    echo "Unpacking ${TARFILE}"
    JREDIR="${NAME}/${IMAGE_TYPE}"
    [ x$NAME != x -a -e "${JREDIR}" ] && $RM -rf "${JREDIR}"
    mkdir -p "${JREDIR}"
    if [ x$OS = xwindows ]; then
      echo "using unzip"
      unzip-strip "${TARFILE}" "${JREDIR}"
      RET=$?
    else
      echo "using tar"
      tar --strip-components=1 -C "${JREDIR}" -zxf "${TARFILE}"
      RET=$?
    fi
    if [ "$RET" != 0 ]; then
      echo "Error unpacking ${TARFILE}"
      exit 1
    fi
    $RM "${TARFILE}"
  done
done

