#!/bin/bash

current=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

pushd "$current/.."

echo "running download..."
./gradlew :downloader:run

echo "now converting the images..."
pushd assets/data/

mkdir images

for x in `ls lorcania_images | grep _small_`;
do
	original=$x
	new=${x%.*}.jpeg

	convert lorcania_images/$original -geometry 200x images/$new;
done

popd

./gradlew :resources:generateMR

# going back to the original path
popd