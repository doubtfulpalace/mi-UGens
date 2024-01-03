#
# Build all projects
# Usage: build.sh <SUPERCOLLIDER SOURCE>
#
SC_SRC=$1

# FOLDERS=(MiBraids MiClouds MiElements MiGrids MiMu MiOmi MiPlaits MiPlaitsVirtualAnalog MiPlaitsWaveshaping MiPlaitsWaveshaping MiPlaitsFM MiPlaitsGrain MiPlaitsAdditive MiPlaitsWavetable MiPlaitsChord MiPlaitsSpeech MiPlaitsSwarm MiPlaitsNoise MiPlaitsParticle MiPlaitsString MiPlaitsModal MiPlaitsBassDrum MiPlaitsSnareDrum MiPlaitsHiHat MiRings MiTides MiVerb MiWarps)

FOLDERS=(MiBraids MiClouds MiElements MiGrids MiMu MiOmi MiPlaits MiRings MiTides MiVerb MiWarps)

MI_UGENS=build/mi-UGens

mkdir -p $MI_UGENS

# MiBraids depends on libsamplerate, let's build that first
cd MiBraids/libsamplerate
echo "Building libsamplerate"
mkdir -p build
cd build
cmake -DCMAKE_BUILD_TYPE=Release -DLIBSAMPLERATE_EXAMPLES=OFF -DCMAKE_APPLE_SILICON_PROCESSOR=arm64 -DBUILD_TESTING=OFF ..
make
cd ../../..


for FOLDER in "${FOLDERS[@]}"
do
	cd $FOLDER

	echo "Building $FOLDER"

	# # Build folder
	mkdir -p build
	cd build

	# # Build
	cmake -DSC_PATH=$SC_SRC -DCMAKE_BUILD_TYPE=Release -DCMAKE_APPLE_SILICON_PROCESSOR=arm64 ..
	cmake --build . --config Release

	# # copy binaries
	if [[ "$OSTYPE" == "darwin"* ]] ; then
		echo Copying *.scx to `pwd`/../../$MI_UGENS
		cp *.scx ../../$MI_UGENS
    elif [[ "$OSTYPE" == "linux-gnu"* ]] ; then
        cp $FOLDER.so ../../$MI_UGENS
	else
		cp Release/${FOLDER}.scx ../../$MI_UGENS
	fi

	# # Return
	cd ../..
done

cp -r sc/* $MI_UGENS
