#!/usr/bin/env zsh


#####################################################################
#
# Script that starts the launching RTB Software Suites.
#
#     Script name:  rtb.sh
#     Purpose:      This shell script starts a launcher
#    Arguments:    None
#
# Copyright (C) 2021-2022 RTB
# All Rights Reserved.
#
#####################################################################

LAUNCH_HOME=$(pwd)
#echo $LAUNCH_HOME

RTB="${LAUNCH_HOME}/jars/rtb.jar"
#echo "$RTB"

CLASSPATH="${RTB}:${LAUNCH_HOME}/jars/jtk.jar:${LAUNCH_HOME}/jars/commons-io-2.4.jar:${LAUNCH_HOME}/jars/NhashCLT.jar:${LAUNCH_HOME}/jars/jackson2.11.3/jackson-core-2.11.3.jar:${LAUNCH_HOME}/jars/jackson2.11.3/jackson-databind-2.11.3.jar:${LAUNCH_HOME}/jars/jackson2.11.3/jackson-annotations-2.11.3.jar"

LIBRARYPATH=${LAUNCH_HOME}/lib
RESOURCESPATH=${LAUNCH_HOME}/resources
HISTORYPATH=${LAUNCH_HOME}/history

#echo "$CLASSPATH"
if [ $# == 0 ] 
then
    ${LAUNCH_HOME}/jre/bin/java -d64 -Xms512m -Xmx4096m -showversion -Drtb.dir=$LAUNCH_HOME -Djava.library.path=$LIBRARYPATH -Djava.resources.path=$RESOURCESPATH -Djava.history.path=$HISTORYPATH -cp $CLASSPATH edu.uth.app.launcher.LauncherApp
fi

if [ $# == 2 ] 
then
	echo "All Arguments values:" $@
	tool_id=$1 
	input_file=$2
	dictionary_file=$3
    ${LAUNCH_HOME}/jre/bin/java -d64 -Xms512m -Xmx4096m -showversion -Drtb.dir=$LAUNCH_HOME -Djava.library.path=$LIBRARYPATH -Djava.resources.path=$RESOURCESPATH -Djava.history.path=$HISTORYPATH -cp $CLASSPATH edu.uth.app.qac.QacApp $tool_id $input_file $dictionary_file
fi
