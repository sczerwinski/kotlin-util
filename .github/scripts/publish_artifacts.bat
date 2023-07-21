@echo off

set SUFFIX=%1

echo Publishing for %BUILD_OS%

if [%SUFFIX%] == [] (
  gradlew publish "-Psigning.secretKeyRingFile=secring.gpg" "-Psigning.password=%SIGNING_PASS%" "-Psigning.keyId=%SIGNING_KEY_ID%" --stacktrace
) else (
  gradlew publish "-PversionSuffix=%SUFFIX%" "-Psigning.secretKeyRingFile=secring.gpg" "-Psigning.password=%SIGNING_PASS%" "-Psigning.keyId=%SIGNING_KEY_ID%" --stacktrace
)

echo on
