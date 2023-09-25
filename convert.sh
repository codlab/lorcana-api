#!/bin/bash


echo "running download..."
./gradlew :downloader:run

echo "now converting the images..."
pushd src/data/

mkdir images

for x in `ls lorcania_images | grep _small_`;
do
	original=$x
	new=${x%.*}.png

	convert lorcania_images/$original -geometry 150x images/$new;
done

popd

./gradlew :resources:generateMR
