########################################################################
#
# THIS FILE WILL NOT BE INCLUDED IN THE BUILD AUTOMATICALLY!!!
#
# To include the profile snippet in the agent bundle you have to build
# the project with profile include-profile, e.g.:
#   mvn -P include-profile clean package
# or change the pom.xml so that include-profile is active by default
#
# In APM 10.2 ACC will only use a file called IntroscopeAgent.profile.
# This file is named differently in order to avoid inadvertently
# overwriting the real IntroscopeAgent.profile when manually extracting
# the archive. In order for ACC to handle this file as designed this
# file has to be renamed in the archive.
#

#<preamble>
########################################################################
#
# Introscope Agent Configuration for ${artifactId} {$version}
#
# Author: ${env.USERNAME} (first.last@ca.com)
# Date: ${date}
# Version: ${version}
# Changes:
#     DATE     |       AUTHOR        |       COMMENTS
#-----------------------------------------------------------------------
#              |                     |
########################################################################
#</preamble>

#<property>

# This describes the following property mybundle

#</property>
# uncomment and change property name and value for your field pack
#mybundle.enable=true
