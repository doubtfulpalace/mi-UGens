// volker böhm, 2019 - https://vboehm.net

MiPlaits : MultiOutUGen {

	*ar {
		arg pitch=60.0, engine=0, harm=0.1, timbre=0.5, morph=0.5, trigger=0.0, level=0, fm_mod=0.0, timb_mod=0.0,
		morph_mod=0.0, decay=0.5, lpg_colour=0.5, mul=1.0;
		^this.multiNew('audio', pitch, engine, harm, timbre, morph, trigger, level, fm_mod, timb_mod, morph_mod,
			decay, lpg_colour).madd(mul);
	}
	//checkInputs { ^this.checkSameRateAsFirstInput }

	init { arg ... theInputs;
		inputs = theInputs;
		^this.initOutputs(2, rate);
	}
}

/*
Pair of classic waveforms

Virtual-analog synthesis of classic waveforms.
detune (HARMONICS): detuning between the two waves.
square_shape (TIMBRE): variable square, from narrow pulse to full square to hardsync formants.
saw_shape (MORPH): variable saw, from triangle to saw with an increasingly wide notch (Braids’ CSAW).
AUX: sum of two hardsync’ed waveforms, the shape of which is controlled by MORPH and de- tuning by HARMONICS.
A narrow pulse or wide notch results in silence!
*/
MiPlaitsVirtualAnalog : MultiOutUGen {

	*ar {
		arg pitch=60.0, detune=0.5, square_shape=0.5, saw_shape=0.5,
		trigger=0.0, level=0, fm_mod=0.0, timb_mod=0.0, morph_mod=0.0, lpg_decay=0.5, lpg_colour=0.5, mul=1.0;
		^this.multiNew('audio', pitch, 0, detune, square_shape, saw_shape,
		trigger, level, fm_mod, timb_mod, morph_mod, lpg_decay, lpg_colour).madd(mul);
	}
	//checkInputs { ^this.checkSameRateAsFirstInput }

	init { arg ... theInputs;
		inputs = theInputs;
		^this.initOutputs(2, rate);
	}
}

/*
Waveshaping oscillator

An asymmetric triangle processed by a waveshaper and a wavefolder.
waveform (HARMONICS): waveshaper waveform.
fold (TIMBRE): wavefolder amount.
asymmetry (MORPH): waveform asymmetry.
AUX: variant employing another wavefolder curve.
*/
MiPlaitsWaveshaping : MultiOutUGen {

	*ar {
		arg pitch=60.0, waveform=0.5, fold=0.5, asymmetry=0.5, trigger=0.0,
		level=0, fm_mod=0.0, timb_mod=0.0, morph_mod=0.0, lpg_decay=0.5, lpg_colour=0.5, mul=1.0;
		^this.multiNew('audio', pitch, 1, waveform, fold, asymmetry,
		trigger, level, fm_mod, timb_mod, morph_mod, lpg_decay, lpg_colour).madd(mul);
	}
	//checkInputs { ^this.checkSameRateAsFirstInput }

	init { arg ... theInputs;
		inputs = theInputs;
		^this.initOutputs(2, rate);
	}
}

/*
Two operator FM

Two sine-wave oscillators modulating each other’s phase.
ratio (HARMONICS): frequency ratio.
index (TIMBRE): modulation index.
feedback (MORPH): feedback, in the form of operator 2 modulating its own phase (past 0.5, rough!) or operator 1’s phase (before 0.5, chaotic!).
AUX: sub-oscillator.
*/
MiPlaitsFM : MultiOutUGen {

	*ar {
		arg pitch=60.0, ratio=0.5, index=0.5, feedback=0.5, trigger=0.0,
		level=0, fm_mod=0.0, timb_mod=0.0, morph_mod=0.0, lpg_decay=0.5, lpg_colour=0.5, mul=1.0;
		^this.multiNew('audio', pitch, 2, ratio, index, feedback,
		trigger, level, fm_mod, timb_mod, morph_mod, lpg_decay, lpg_colour).madd(mul);
	}
	//checkInputs { ^this.checkSameRateAsFirstInput }

	init { arg ... theInputs;
		inputs = theInputs;
		^this.initOutputs(2, rate);
	}
}

/*
Granular formant oscillator

Simulation of formants and filtered waveforms through the multiplication, addition and synchronization of segments of sine waves.
ratio (HARMONICS): frequency ratio between formant 1 and 2.
formant_freq (TIMBRE): formant frequency.
formant_shape (MORPH): formant width and shape.
AUX: simulation of filtered waveforms by windowed sine waves – a recreation of Braids’ Z*** models. ratio (HARMONICS) controls the filter type (peaking, LP, BP, HP).
*/
MiPlaitsGrain : MultiOutUGen {

	*ar {
		arg pitch=60.0, ratio=0.5, formant_freq=0.5, formant_shape=0.5,
		trigger=0.0, level=0, fm_mod=0.0, timb_mod=0.0, morph_mod=0.0, lpg_decay=0.5, lpg_colour=0.5, mul=1.0;
		^this.multiNew('audio', pitch, 3, ratio, formant_freq, formant_shape,
		trigger, level, fm_mod, timb_mod, morph_mod, lpg_decay, lpg_colour).madd(mul);
	}
	//checkInputs { ^this.checkSameRateAsFirstInput }

	init { arg ... theInputs;
		inputs = theInputs;
		^this.initOutputs(2, rate);
	}
}

/*
Harmonic oscillator

An additive mixture of harmonically-related sine waves.
richness (HARMONICS): number of bumps in the spectrum.
index (TIMBRE): index of the most prominent harmonic.
resonance (MORPH): bump shape – from flat and wide to peaked and narrow.
AUX: variant including only the subset of harmonics present in the drawbars of a Hammond organ.
*/
MiPlaitsAdditive : MultiOutUGen {

	*ar {
		arg pitch=60.0, richness=0.5, index=0.5, resonance=0.5, trigger=0.0,
		level=0, fm_mod=0.0, timb_mod=0.0, morph_mod=0.0, lpg_decay=0.5, lpg_colour=0.5, mul=1.0;
		^this.multiNew('audio', pitch, 4, richness, index, resonance,
		trigger, level, fm_mod, timb_mod, morph_mod, lpg_decay, lpg_colour).madd(mul);
	}
	//checkInputs { ^this.checkSameRateAsFirstInput }

	init { arg ... theInputs;
		inputs = theInputs;
		^this.initOutputs(2, rate);
	}
}

/*
Wavetable oscillator

Four banks of 8x8 waveforms, accessed by row and column, with or without interpolation.

bank (HARMONICS): bank selection.
row (TIMBRE): row index. Within a row, the waves
are sorted by spectral brightness.
column (MORPH): column index.
AUX: low-fi output.
*/
MiPlaitsWavetable : MultiOutUGen {

	*ar {
		arg pitch=60.0, bank=0.5, row=0.5, column=0.5, trigger=0.0,
		level=0, fm_mod=0.0, timb_mod=0.0, morph_mod=0.0, lpg_decay=0.5, lpg_colour=0.5, mul=1.0;
		^this.multiNew('audio', pitch, 5, bank, row, column,
		trigger, level, fm_mod, timb_mod, morph_mod, lpg_decay, lpg_colour).madd(mul);
	}
	//checkInputs { ^this.checkSameRateAsFirstInput }

	init { arg ... theInputs;
		inputs = theInputs;
		^this.initOutputs(2, rate);
	}
}
/*
Chords

Four-note chords, played by VA or wavetable oscillators.

chord (HARMONICS): chord type (oct, 5, sus4, m, m7, m9, m11, 6/9, M9, M7, M).
inversion (TIMBRE): chord inversion and transposition.
waveform (MORPH): waveform. The first half of the knob goes through a selection of string-machine like raw waveforms, the second half of the knob scans a small wavetable.
AUX: root note of the chord.
*/
MiPlaitsChord : MultiOutUGen {

	*ar {
		arg pitch=60.0, chord=0.0, inversion=0.0, waveform=0.5, trigger=0.0,
		level=0, fm_mod=0.0, timb_mod=0.0, morph_mod=0.0, lpg_decay=0.5,
		lpg_colour=0.5, mul=1.0;
		^this.multiNew('audio', pitch, 6, chord, inversion, waveform,
		trigger, level, fm_mod, timb_mod, morph_mod, lpg_decay,
		lpg_colour).madd(mul);
	}
	//checkInputs { ^this.checkSameRateAsFirstInput }

	init { arg ... theInputs;
		inputs = theInputs;
		^this.initOutputs(2, rate);
	}
}


/*
Vowel and speech synthesis

A collection of speech synthesis algorithms.

utterance (HARMONICS): crossfades between formant filtering, SAM, and LPC vowels, then goes through several banks of LPC words.
species (TIMBRE): species selection, from Daleks to chipmunks.
segment (MORPH): phoneme or word segment selection.
intonation (FM attenuverter): intonation.
speed (MORPH attenuverter): speed.
Patch the trigger input to trigger the utterance of a word.
AUX: unfiltered vocal cords’ signal.
*/
MiPlaitsSpeech : MultiOutUGen {

	*ar {
		arg pitch=60.0, utterance=0.0, species=0.5, segment=0.0, trigger=0.0,
		level=0, intonation=0.0, speed=0.0, morph_mod=0.0, lpg_decay=0.5,
		lpg_colour=0.5, mul=1.0;
		^this.multiNew('audio', pitch, 7, utterance, species, segment,
		trigger, level, intonation, speed, morph_mod, lpg_decay,
		lpg_colour).madd(mul);
	}
	//checkInputs { ^this.checkSameRateAsFirstInput }

	init { arg ... theInputs;
		inputs = theInputs;
		^this.initOutputs(2, rate);
	}
}

/*
Granular cloud

A swarm of 8 enveloped sawtooth waves.

detune (HARMONICS): amount of pitch randomization.
density (TIMBRE): grain density.
overlap (MORPH): grain duration and overlap.
AUX: variant with sine wave oscillators.
*/
MiPlaitsSwarm : MultiOutUGen {

	*ar {
		arg pitch=60.0, detune=0.5, density=0.5, overlap=0.5, trigger=0.0,
		level=0, fm_mod=0.0, timb_mod=0.0, morph_mod=0.0, lpg_decay=0.5, lpg_colour=0.5, mul=1.0;
		^this.multiNew('audio', pitch, 8, detune, density, overlap,
		trigger, level, fm_mod, timb_mod, morph_mod, lpg_decay, lpg_colour).madd(mul);
	}
	//checkInputs { ^this.checkSameRateAsFirstInput }

	init { arg ... theInputs;
		inputs = theInputs;
		^this.initOutputs(2, rate);
	}
}

/*
Filtered noise

Variable-clock white noise processed by a resonant filter.

shape (HARMONICS): filter response, from LP to BP to HP.
clock_freq (TIMBRE): clock frequency.
resonance (MORPH): filter resonance.
AUX: variant processed by two band-pass filters, with their separation controlled by shape (HARMONICS).
*/
MiPlaitsNoise : MultiOutUGen {

	*ar {
		arg pitch=60.0, shape=0.5, clock_freq=0.5, resonance=0.5, trigger=0.0,
		level=0, fm_mod=0.0, timb_mod=0.0, morph_mod=0.0, lpg_decay=0.5, lpg_colour=0.5, mul=1.0;
		^this.multiNew('audio', pitch, 9, shape, clock_freq, resonance,
		trigger, level, fm_mod, timb_mod, morph_mod, lpg_decay, lpg_colour).madd(mul);
	}
	//checkInputs { ^this.checkSameRateAsFirstInput }

	init { arg ... theInputs;
		inputs = theInputs;
		^this.initOutputs(2, rate);
	}
}

/*
Particle noise

Dust noise processed by networks of all-pass or band-pass filters.

spread (HARMONICS): amount of frequency randomization.
density (TIMBRE): particle density.
filter_type (MORPH): filter type – reverberating all-pass network before 12 o’clock, then increasingly resonant band-pass filters.
AUX: raw dust noise.
*/
MiPlaitsParticle : MultiOutUGen {

	*ar {
		arg pitch=60.0, spread=0.5, density=0.5, filter_type=0.5, trigger=0.0,
		level=0, fm_mod=0.0, timb_mod=0.0, morph_mod=0.0, lpg_decay=0.5, lpg_colour=0.5, mul=1.0;
		^this.multiNew('audio', pitch, 10, spread, density, filter_type,
		trigger, level, fm_mod, timb_mod, morph_mod, lpg_decay, lpg_colour).madd(mul);
	}
	//checkInputs { ^this.checkSameRateAsFirstInput }

	init { arg ... theInputs;
		inputs = theInputs;
		^this.initOutputs(2, rate);
	}
}

/*
missing from manual...
*/
MiPlaitsString : MultiOutUGen {

	*ar {
		arg pitch=60.0, structure=0.5, brightness=0.5, damping=0.5, trigger=0.0,
		level=0, fm_mod=0.0, timb_mod=0.0, morph_mod=0.0, mul=1.0;
		^this.multiNew('audio', pitch, 11, structure, brightness, damping,
		trigger, level, fm_mod, timb_mod, morph_mod, 1, 1).madd(mul);
	}
	//checkInputs { ^this.checkSameRateAsFirstInput }

	init { arg ... theInputs;
		inputs = theInputs;
		^this.initOutputs(2, rate);
	}
}

/*
Modal resonator bank

For your own pleasure, a mini-Rings! When the TRIG input is not patched, the resonator is excited by dust noise.

inharmonicity (HARMONICS): amount of inharmonicity, or material selection.
brightness (TIMBRE): excitation brightness and dust density.
decay (MORPH): decay time (energy absorption).
AUX: raw exciter signal.
*/
MiPlaitsModal : MultiOutUGen {

	*ar {
		arg pitch=60.0, inharmonicity=0.5, brightness=0.5, decay=0.5,
		trigger=0.0, level=0, fm_mod=0.0, timb_mod=0.0, morph_mod=0.0, mul=1.0;
		^this.multiNew('audio', pitch, 12, inharmonicity, brightness, decay,
		trigger, level, fm_mod, timb_mod, morph_mod, 1, 1).madd(mul);
	}
	//checkInputs { ^this.checkSameRateAsFirstInput }

	init { arg ... theInputs;
		inputs = theInputs;
		^this.initOutputs(2, rate);
	}
}

/*
Analog bass drum model

snappy (HARMONICS): attack sharpness.
brightness (TIMBRE): brightness.
decay (MORPH): decay time.
AUX: emulation of another classic bass drum circuit.
*/
MiPlaitsBassDrum : MultiOutUGen {

	*ar {
		arg pitch=60.0, snappy=0.5, brightness=0.5, decay=0.5, trigger=0.0,
		level=0, fm_mod=0.0, timb_mod=0.0, morph_mod=0.0, mul=1.0;
		^this.multiNew('audio', pitch, 13, snappy, brightness, decay,
		trigger, level, fm_mod, timb_mod, morph_mod, 1, 1).madd(mul);
	}
	//checkInputs { ^this.checkSameRateAsFirstInput }

	init { arg ... theInputs;
		inputs = theInputs;
		^this.initOutputs(2, rate);
	}
}

/*
Analog snare drum model

noise_balance (HARMONICS): balance of the harmonic and noisy components.
mode_balance (TIMBRE): balance between the different modes of the drum.
decay (MORPH): decay time.
AUX: emulation of another classic snare drum circuit.
*/
MiPlaitsSnareDrum : MultiOutUGen {

	*ar {
		arg pitch=60.0, noise_balance=0.5, mode_balance=0.5, decay=0.5,
		trigger=0.0, level=0, fm_mod=0.0, timb_mod=0.0, morph_mod=0.0, mul=1.0;
		^this.multiNew('audio', pitch, 14, noise_balance, mode_balance, decay,
		trigger, level, fm_mod, timb_mod, morph_mod, 1, 1).madd(mul);
	}
	//checkInputs { ^this.checkSameRateAsFirstInput }

	init { arg ... theInputs;
		inputs = theInputs;
		^this.initOutputs(2, rate);
	}
}

/*
Analog high-hat model

noise_balance (HARMONICS): balance of the metallic and filtered noise.
hp_cutoff (TIMBRE): high-pass filter cutoff.
decay (MORPH): decay time.
AUX: variant with a different flavor of tuned noise based on ring-modulated square waves.
*/
MiPlaitsHiHat : MultiOutUGen {

	*ar {
		arg pitch=60.0, noise_balance=0.5, hp_cutoff=0.5, decay=0.5,
		trigger=0.0, level=0, fm_mod=0.0, timb_mod=0.0, morph_mod=0.0, mul=1.0;
		^this.multiNew('audio', pitch, 15, noise_balance, hp_cutoff, decay,
		trigger, level, fm_mod, timb_mod, morph_mod, 1, 1).madd(mul);
	}
	//checkInputs { ^this.checkSameRateAsFirstInput }

	init { arg ... theInputs;
		inputs = theInputs;
		^this.initOutputs(2, rate);
	}
}
