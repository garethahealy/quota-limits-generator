#!/usr/bin/env bash

echo "Deploying code signing key..."

cd ./.travis

openssl aes-256-cbc -K $encrypted_2190c0b783e5_key -iv $encrypted_2190c0b783e5_iv -in codesigning.asc.enc -out codesigning.asc -d
gpg --fast-import codesigning.asc

cd ../
