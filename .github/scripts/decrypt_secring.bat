@echo off
gpg --quiet --batch --yes --decrypt --passphrase="%SIGNING_KEY_FILE_PASSPHRASE%" --output secring.gpg secring.gpg.gpg
echo on
