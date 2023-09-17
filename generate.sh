ASSETS_PATH="assets"
IMG_PATH="lorcana-apps/iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/ic_launcher_foreground-"
LOGO="$ASSETS_PATH/logo.png"

array=("20" "29" "40" "58" "60" "76" "80" "87" "120" "152" "167" "180")
for x in ${array[@]}; do
  convert "$LOGO" -resize ${x}x${x} "${IMG_PATH}${x}x${x}.png"
done
# only the 1024 is different as we don't want alpha channel
convert "$LOGO" -resize 1024x1024 -alpha deactivate "${IMG_PATH}1024x1024.png"

convert "$LOGO" -resize 512x512 "lorcana-apps/jvmApp/icon.png"

# preparing iconset for macos

mkdir MyIcon.iconset
sips_array=("16" "32" "32" "64" "128" "256" "256" "512" "512")
for x in ${sips_array[@]}; do
  sips -z ${x} ${x}     "$LOGO" --out MyIcon.iconset/icon_${x}x${x}.png
done
cp "$LOGO" MyIcon.iconset/icon_512x512@2x.png
iconutil -c icns MyIcon.iconset
rm -R MyIcon.iconset
mv MyIcon.icns lorcana-apps/jvmApp/icon.icns
