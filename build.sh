#!/bin/sh

# Sync with Transifex
/transifex/sync_transifex.sh \
  --resource openlmis-example.messages \
  --pattern 'src/main/resources/messages_<lang>.properties' \
  --source-file src/main/resources/messages_en.properties

# Run Gradle build
gradle clean generateBuildInfo build
