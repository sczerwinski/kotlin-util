@echo off

set BUILD_OS=%1

echo Publishing for %BUILD_OS%

if "%BUILD_OS:~0,6%"=="ubuntu" (
  set TASKS=publishLinuxMips32PublicationToMavenRepository publishLinuxMipsel32PublicationToMavenRepository
) else if "%BUILD_OS:~0,7%"=="windows" (
  set TASKS=publishMingwX64PublicationToMavenRepository publishMingwX86PublicationToMavenRepository
) else if "%BUILD_OS:~0,5%"=="macos" (
  set TASKS=publish
)

echo Running tasks %TASKS%
echo.

./gradlew %TASKS% "-Psigning.secretKeyRingFile=secring.gpg" "-Psigning.password=%SIGNING_PASS%" "-Psigning.keyId=%SIGNING_KEY_ID%" --stacktrace

echo on
