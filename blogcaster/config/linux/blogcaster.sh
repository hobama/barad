#!/bin/bash
# $RCSfile$    $Revision$  $Date$ - $Author$
# 
# Copyright (c) 2005 - Michael King, Kevin McAllister
# 
# This library is free software; you can redistribute it and/or modify
# it under the terms of the GNU Lesser General Public License as
# published by the Free Software Foundation; either version 2.1 of the
# License, or (at your option) any later version. See the file
# LICENSE.txt included with this library for more information.

# This file is a simple bash script used to launch Blogcaster on Linux
# Systems.  If you already have a java environment set up it will
# attempt to use it, otherwise you will want to set the JAVA_HOME env
# variable in this file.

# Blogcaster probably will only work with Java 1.4 or better
PROJECT="Blogcaster"
JARFILE="${PROJECT}.jar"
SCRIPTDIR=`dirname $0`
LOGFILE="${SCRIPTDIR}/${PROJECT}.out"

#JAVA_HOME=/usr/local/jdk

if [ x$JAVA_HOME != 'x' ]; then
    if [ -f $JAVA_HOME/bin/java ]; then
	JAVA=$JAVA_HOME/bin/java
    fi
else # JAVA_HOME no good, look on the path
    JAVA=`which java 2>/dev/null`
    if [ $? -gt 0 ]; then
	    # there ain't no java here
	echo "Can't find java binary set JAVA_HOME."
	exit 1
    fi
fi

# If we made it this far we probably have some kind of Java binary
# to try and execute, so might as well execute it.
LIBPATH="$SCRIPTDIR/native"
SETTPATH="${SCRIPTDIR}/"

if [ x$JAVA != "x" ] && [ -x ${JAVA} ]; then
    ${JAVA} -Djava.library.path=${LIBPATH} \
	-Dblogcaster.settingspath=${SETTPATH} \
	-jar ${SCRIPTDIR}/${JARFILE} \
	> ${LOGFILE} 2>&1
else
    echo "Cannot execute JAVA: ${JAVA}."
fi

RET=$?
if [ $RET -gt 0 ];  then
    echo "Could not run $PROJECT, check ${LOGFILE} for error messages."
fi

exit $RET
