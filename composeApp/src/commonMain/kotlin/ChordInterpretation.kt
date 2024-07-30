// TODO create chord tests
// food for thought, when adding score based on intervals:
// when considering an extended chord with a missing 5th (C E X Bb D F A)
// vs the same pitch classes spelled without any missing 5th (Bb D F A C E),
// if the C and E are lower octaves, it should be C13
// if the Bb is lower than the C, it should be Bbmaj9(#11)
// (A D F) should be D chord first inversion
// (G D F) should be G7(no3) not Gm7(no3)
// (G F A) should be G7sus2
// (G F A C) should be Gsus9 or G9sus4? (abbreviation for G7sus4(9), a sus4 chord with 7 and 9)
// C# E G Bb for C# diminished, and C# E G# A# for C#m6
// G Bb D F# for Gm(maj7)
// C E G B B should be Cmaj7 not Em/C
// C F A C Eb G should probably be F9/C not Cm6add11
// test with two unison notes, one note going chromatically up to octave
// chords without 5 but with 3rd and 7th should still be pretty relevant
// should C G B D F A be C13(no 3) or G13 ??? suspended ??? if second / fourth is below fifth / seventh? prob C sus
// should be Eb minor and not D# minor, because D# minor is the relative minor of B#
// should be first inversion major chord, not minor chord with a b6
// TODO bass bonus relevancy?
// TODO chord's conceptual structure vs intervallic structure
// TODO advanced settings to hide things for first time user
// TODO polychords
// TODO bass notes, slash chords, inversions
// TODO bonus score for designated chord structures?
// TODO decide between chord quality: major, minor, suspended, augmented, split 3rd
// TODO double sharp and double flat symbols, fancier sharp and flat symbols, natural symbol
// TODO "sets" ( 0 1 7 ) ( 0 6 7 ) etc
// TODO "chord" shows up when there's just an octave or unison?

// TODO @Serializable
// TODO order pitches by midiValue, in the case that a higher string is a lower pitch???
class ChordInterpretation(
    val root: Pitch,
    val bassNote: Pitch,
    val pitches: List<Pitch>
) {
    companion object {
        // constants for relevancy score
        const val octaveScoringFactor: Float = 1.0f
        const val duplicateNoteScoringFactor: Float = 0.5f
        const val fullScore = 30.0f
        const val halfScore = 15.0f
        const val bassNoteBonus = 15.0f

        // chromatic interval values
        const val perfectUnison: Int = 0
        const val minorSecond: Int = 1
        const val majorSecond: Int = 2
        const val augmentedSecond: Int = 3
        const val minorThird: Int = 3
        const val majorThird: Int = 4
        const val perfectFourth: Int = 5
        const val augmentedFourth: Int = 6
        const val diminishedFifth: Int = 6
        const val perfectFifth: Int = 7
        const val augmentedFifth: Int = 8
        const val minorSixth: Int = 8
        const val majorSixth: Int = 9
        const val augmentedSixth: Int = 10
        const val diminishedSeventh: Int = 9
        const val minorSeventh: Int = 10
        const val majorSeventh: Int = 11

        // letter interval values
        const val unison: Int = 0
        const val second: Int = 1
        const val third: Int = 2
        const val fourth: Int = 3
        const val fifth: Int = 4
        const val sixth: Int = 5
        const val seventh: Int = 6
    }

    var chosenRoot: Pitch = root.copy()
    val chosenPitches: List<Pitch> = pitches.map { it.copy() }
    val intervals: List<ChordInterpretationHelper> = (0..11).map {
        ChordInterpretationHelper()
    }
    var relevancyScore: Float = 0f
    var chordQuality: String = ""
    var chordType: String = ""
    var chordName: String = ""
    var extensionsPrefix: String = ""
    var extensions: List<String> = listOf()

    init {
        for ((index, pitch) in chosenPitches.withIndex()) {
            val interval: Int = pitch.intervalFrom(root)

            if (!intervals[interval].inChord) {
                intervals[interval].inChord = true
                intervals[interval].lowestPitchIndex = index
            }
            else if (pitches[index].midiValue < pitches[intervals[interval].lowestPitchIndex].midiValue) {
                intervals[interval].duplicatePitchIndexes.add(intervals[interval].lowestPitchIndex)
                intervals[interval].lowestPitchIndex = index
            }
            else {
                intervals[interval].duplicatePitchIndexes.add(index)
            }

        }

        // default pairings of chromatic intervals with letter intervals, updated later when necessary
        intervals[perfectUnison].letterInterval = unison
        intervals[minorSecond].letterInterval = second
        intervals[majorSecond].letterInterval = second
        intervals[minorThird].letterInterval = third
        intervals[majorThird].letterInterval = third
        intervals[perfectFourth].letterInterval = fourth
        intervals[augmentedFourth].letterInterval = fourth
        intervals[perfectFifth].letterInterval = fifth
        intervals[minorSixth].letterInterval = sixth
        intervals[majorSixth].letterInterval = sixth
        intervals[minorSeventh].letterInterval = seventh
        intervals[majorSeventh].letterInterval = seventh

        // TODO convert equation so instead of octave, based on raw midiValue
        fun applyRelevancy(interval: Int, score: Float) {

            relevancyScore += score - chosenPitches[intervals[interval].lowestPitchIndex].octave * octaveScoringFactor

            if (intervals[interval].duplicatePitchIndexes.isNotEmpty()) {
                for (index in intervals[interval].duplicatePitchIndexes) {
                    relevancyScore += ( score - chosenPitches[index].octave * octaveScoringFactor ) * duplicateNoteScoringFactor
                }
            }

        }

        fun applyExtensions(addOrMod: String, extensionsToOmit: List<Int> = listOf()) {

            val extensionsToCheck: List<Int> = listOf(1,2,3,5,6,8,10)
            var extensionNeeded: Boolean = false
            var listOfExtensions: List<Int> = listOf()

            for (extension in extensionsToCheck) {
                if (intervals[extension].inChord && extension !in extensionsToOmit) {
                    extensionNeeded = true
                    listOfExtensions = listOfExtensions + extension
                }
            }

            if (extensionNeeded) {
                if (diminishedFifth in listOfExtensions &&
                    chordQuality != "diminished" &&
                    chordQuality != "augmented" &&
                    !intervals[perfectFifth].inChord
                    ) {
                    intervals[diminishedFifth].letterInterval = fifth

                    if (addOrMod == "mod") {
                        extensions = extensions + "♭5"
                    }
                    if (addOrMod == "add") {
                        extensionsPrefix += "♭5"
                    }
                }
                if (minorSecond in listOfExtensions) {
                    extensions = extensions + "♭9"
                }
                if (majorSecond in listOfExtensions && chordQuality != "suspended2") {
                    if (addOrMod == "mod" &&
                        (intervals[minorSecond].inChord || intervals[augmentedSecond].inChord) &&
                        (intervals[perfectFourth].inChord || intervals[majorSixth].inChord)
                        ) {
                        extensions = extensions + "♮9"
                    }
                    else if (addOrMod == "add" && !intervals[majorSixth].inChord) {
                        extensions = extensions + "9"
                    }
                }
                if (augmentedSecond in listOfExtensions) {
                    if (intervals[majorThird].inChord) {
                        extensions = extensions + "♯9"
                        // possible subjective reading: intervals[augmentedSecond].letterInterval = second
                    }
                }
                if (perfectFourth in listOfExtensions && chordQuality != "suspended4") {
                    // TODO account for diminished chord with major Sixth
                    if (addOrMod == "mod" &&
                        intervals[augmentedFourth].inChord &&
                        intervals[majorSixth].inChord &&
                        chordQuality != "diminished"
                    ) {
                        extensions = extensions + "♮11"
                    }
                    else if (addOrMod == "add") {
                        extensions = extensions + "11"
                    }
                }
                if (augmentedFourth in listOfExtensions &&
                    chordQuality != "diminished" &&
                    (intervals[perfectFifth].inChord || chordQuality == "augmented")
                    ) {
                    extensions = extensions + "♯11"
                }
                if (minorSixth in listOfExtensions) {
                    if (addOrMod == "mod" && intervals[perfectFifth].inChord) {
                        extensions = extensions + "♭13"
                    }
                    else if (addOrMod == "add" && intervals[perfectFifth].inChord && intervals[majorSixth].inChord) {
                        extensions = extensions + "♭13"
                    }
                }
                if (augmentedSixth in listOfExtensions) {
                    if (intervals[majorSeventh].inChord) {
                        extensions = extensions + "♯13"
                        intervals[augmentedSixth].letterInterval = sixth
                    }
                }
            }

            if (addOrMod == "add" && extensions.isNotEmpty()) {
                extensionsPrefix += "add"
            }
        }

        // apply relevancy for root note(s)
        applyRelevancy(perfectUnison, 20f)

        // apply relevancy bonus for bass note being the root note
        if (root.midiValue == bassNote.midiValue) {
            relevancyScore += bassNoteBonus
        }

        // TODO extensions function that also changes chordType
        // TODO take care of all these intervals here: majorSecond, minorThird, majorThird,
        // TODO split third, 7#9 chord
        // TODO take care of add chords here
        // TODO b6
        //TODO finish add chords b9, 9, #9, 11, #11 (b13?) ♭
        // determine identity of chord while applying relevancy for appropriate intervals
        // TODO addb13 for 6 chord without seventh and with both major and minor sixth?
        // TODO add b6 or just b6? I think add♭6
        // TODO ♭ ♮ ♯ °
        // TODO
        //              applyExtensions("add", listOf(1,2,3, 5,6, 8,9,10))






        // TODO intervals omitted instead of the intervals to check?

        // TODO not every chordQuality used functionally so far

        if (intervals[majorThird].inChord) { chordQuality = "major"; chordType = "";
            applyRelevancy(majorThird, fullScore)

            if (intervals[perfectFifth].inChord || !intervals[augmentedFifth].inChord) {
                if (intervals[perfectFifth].inChord) { applyRelevancy(perfectFifth, fullScore) }

                if (intervals[majorSeventh].inChord) { chordType = "maj7"
                    if (intervals[majorSixth].inChord) { chordType = "maj13" }
                    else if (intervals[perfectFourth].inChord) { chordType = "maj11" }
                    else if (intervals[majorSecond].inChord) { chordType = "maj9" }

                    applyExtensions("mod")
                }
                else if (intervals[minorSeventh].inChord) { chordType = "7"
                    if (intervals[majorSixth].inChord) { chordType = "13" }
                    else if (intervals[perfectFourth].inChord) { chordType = "11" }
                    else if (intervals[majorSecond].inChord) { chordType = "9" }

                    applyExtensions("mod")
                }
                else {
                    if (intervals[majorSixth].inChord) { chordType = "6"
                        if (intervals[majorSecond].inChord) { chordType = "6/9" }
                    }
                    else if (intervals[minorSixth].inChord) { chordType = "(♭6)" }

                    applyExtensions("add")
                }
            }

            else if (intervals[augmentedFifth].inChord) { chordQuality = "augmented"; chordType = "+"
                applyRelevancy(augmentedFifth, fullScore)
                intervals[augmentedFifth].letterInterval = fifth

                if (intervals[majorSeventh].inChord) { chordType = "+maj7"
                    if (intervals[majorSixth].inChord) { chordType = "+maj13" }
                    else if (intervals[perfectFourth].inChord) { chordType = "+maj11" }
                    else if (intervals[majorSecond].inChord) { chordType = "+maj9" }

                    applyExtensions("mod")
                }
                else if (intervals[minorSeventh].inChord) { chordType = "+7"
                    if (intervals[majorSixth].inChord) { chordType = "+13" }
                    else if (intervals[perfectFourth].inChord) { chordType = "+11" }
                    else if (intervals[majorSecond].inChord) { chordType = "+9" }

                    applyExtensions("mod")
                }
                else {
                    if (intervals[majorSixth].inChord) { chordType = "+6"
                        if (intervals[majorSecond].inChord) { chordType = "+6/9" }
                    }

                    applyExtensions("add")
                }
            }
        }
        else if (intervals[minorThird].inChord) { chordQuality = "minor"; chordType = "m";
            applyRelevancy(minorThird, fullScore)

            if (intervals[perfectFifth].inChord || !intervals[diminishedFifth].inChord) {
                if (intervals[perfectFifth].inChord) { applyRelevancy(perfectFifth, fullScore) }

                if (intervals[majorSeventh].inChord) {
                    chordType = "m(maj7)"
                    if (intervals[majorSixth].inChord) { chordType = "m(maj13)" }
                    else if (intervals[perfectFourth].inChord) { chordType = "m(maj11)" }
                    else if (intervals[majorSecond].inChord) { chordType = "m(maj9)" }

                    applyExtensions("mod")
                }
                else if (intervals[minorSeventh].inChord) { chordType = "m7"
                    if (intervals[majorSixth].inChord) { chordType = "m13" }
                    else if (intervals[perfectFourth].inChord) { chordType = "m11" }
                    else if (intervals[majorSecond].inChord) { chordType = "m9" }

                    applyExtensions("mod")
                }
                else {
                    if (intervals[majorSixth].inChord) { chordType = "m6"
                        if (intervals[majorSecond].inChord) { chordType = "m6/9" }
                    }
                    else if (intervals[minorSixth].inChord) { chordType = "m♭6" }

                    applyExtensions("add")
                }
            }

            else if (intervals[diminishedFifth].inChord) { chordQuality = "diminished"; chordType = "°";
                applyRelevancy(diminishedFifth, fullScore)
                intervals[diminishedFifth].letterInterval = fifth

                if (intervals[majorSeventh].inChord) { chordType = "°maj7"
                    if (intervals[majorSixth].inChord) { chordType = "°maj13" }
                    else if (intervals[perfectFourth].inChord) { chordType = "°maj11" }
                    else if (intervals[majorSecond].inChord) { chordType = "°maj9" }

                    applyExtensions("mod")
                }
                else if (intervals[minorSeventh].inChord) { chordType = "ø7"
                    if (intervals[majorSixth].inChord) { chordType = "ø13" }
                    else if (intervals[perfectFourth].inChord) { chordType = "ø11" }
                    else if (intervals[majorSecond].inChord) { chordType = "ø9" }

                    applyExtensions("mod")
                }
                else if (intervals[diminishedSeventh].inChord) { chordType = "°7"; intervals[diminishedSeventh].letterInterval = seventh
                    if (intervals[perfectFourth].inChord) { chordType = "°11" }
                    else if (intervals[majorSecond].inChord) { chordType = "°9" }

                    applyExtensions("mod")
                }
                else {
                    if (intervals[majorSixth].inChord) { chordType = "°6"
                        if (intervals[majorSecond].inChord) { chordType = "°6/9" }
                    }
                    else if (intervals[minorSixth].inChord) { chordType = "°♭6" }

                    applyExtensions("add")
                }
            }
        }
        else if (intervals[perfectFourth].inChord) { chordQuality = "suspended4"; chordType = "sus4";
            applyRelevancy(perfectFourth, halfScore)

            if (intervals[perfectFifth].inChord) { applyRelevancy(perfectFifth, fullScore) }

            if (intervals[majorSeventh].inChord) { chordType = "maj7sus4"
                if (intervals[majorSixth].inChord) { chordType = "maj13sus4" }
                else if (intervals[majorSecond].inChord) { chordType = "maj9sus4" }

                applyExtensions("mod")
            }
            else if (intervals[minorSeventh].inChord) { chordType = "7sus4"
                if (intervals[majorSixth].inChord) { chordType = "13sus4" }
                else if (intervals[majorSecond].inChord) { chordType = "9sus4" }

                applyExtensions("mod")
            }
            else {
                if (intervals[majorSixth].inChord) { chordType = "6sus4"
                    if (intervals[majorSecond].inChord) { chordType = "6/9sus4" }
                }
                else if (intervals[minorSixth].inChord) { chordType = "(♭6)sus4" }

                applyExtensions("add")
            }
        }
        else if (intervals[majorSecond].inChord) { chordQuality = "suspended2"; chordType = "sus2"
            applyRelevancy(majorSecond, halfScore)

            if (intervals[perfectFifth].inChord) { applyRelevancy(perfectFifth, fullScore) }

            if (intervals[majorSeventh].inChord) { chordType = "maj7sus2"
                if (intervals[majorSixth].inChord) { chordType = "maj13sus2" }
                else if (intervals[perfectFourth].inChord) { chordType = "maj11sus2" }

                applyExtensions("mod")
            }
            else if (intervals[minorSeventh].inChord) { chordType = "7sus2"
                if (intervals[majorSixth].inChord) { chordType = "13sus2" }
                else if (intervals[perfectFourth].inChord) { chordType = "11sus2" }

                applyExtensions("mod")
            }
            else {
                if (intervals[majorSixth].inChord) { chordType = "6sus2" }
                else if (intervals[minorSixth].inChord) { chordType = "(♭6)sus2" }

                applyExtensions("add")
            }
        }
        else {
            chordQuality = "other"

            if (intervals[perfectFifth].inChord) { chordType = "(no3)"
                applyRelevancy(perfectFifth, fullScore)

                if (intervals[majorSeventh].inChord) { chordType = "maj7(no3)"
                    if (intervals[majorSixth].inChord) { chordType = "maj13(no3)" }

                    applyExtensions("mod")
                }
                else if (intervals[minorSeventh].inChord) { chordType = "7(no3)"
                    if (intervals[majorSixth].inChord) { chordType = "13(no3)" }

                    applyExtensions("mod")
                }
                else {
                    if (intervals[majorSixth].inChord) { chordType = "6(no3)" }
                    else if (intervals[minorSixth].inChord) { chordType = "(♭6)(no3)" }

                    applyExtensions("add")
                }
            }
            else if (intervals[diminishedFifth].inChord) { chordQuality = "diminished"; chordType = "°(no3)"
                applyRelevancy(diminishedFifth, fullScore)
                intervals[diminishedFifth].letterInterval = fifth

                if (intervals[majorSeventh].inChord) { chordType = "°maj7(no3)"
                    if (intervals[majorSixth].inChord) { chordType = "°maj13(no3)" }

                    applyExtensions("mod")
                }
                else if (intervals[minorSeventh].inChord) { chordType = "ø7(no3)"
                    if (intervals[majorSixth].inChord) { chordType = "ø13(no3)" }

                    applyExtensions("mod")
                }
                else if (intervals[diminishedSeventh].inChord) { chordType = "°7(no3)"; intervals[diminishedSeventh].letterInterval = seventh
                    applyExtensions("mod")
                }
                else {
                    if (intervals[minorSixth].inChord) { chordType = "°♭6(no3)" }

                    applyExtensions("add")
                }
            }
            else if (intervals[majorSeventh].inChord) { chordType = "maj7(no3)"
                applyRelevancy(majorSeventh, halfScore)
                applyExtensions("mod")
            }
            else if (intervals[minorSeventh].inChord) { chordType = "7(no3)"
                applyRelevancy(minorSeventh, halfScore)
                applyExtensions("mod")
            }
            else { chordType = "(no3)"
                if (intervals[majorSixth].inChord) { chordType = "6(no3)" }
                else if (intervals[minorSixth].inChord) { chordType = "(♭6)(no3)" }

                applyExtensions("add")
            }
        }

        // TODO move chosenReading idea into a pitchInterpretations variable in chord that keeps track of the root,
        // TODO and possible natural, sharp, flat interpretations
        // determine pitch readings
        if (chosenRoot.hasNatural) {
            chosenRoot.chosenReading = chosenRoot.naturalReading

            for (pitch in chosenPitches) {
                if (pitch.midiValue == chosenRoot.midiValue) {
                    pitch.chosenReading = chosenRoot.chosenReading
                }
                else {
                    pitch.chosenReading = PitchSpelling(
                        pitch.midiValue % 12,
                        chosenRoot.chosenReading.pitchLetter.letterAtInterval(intervals[((pitch.midiValue - chosenRoot.midiValue).mod(12))].letterInterval),
                        Accidental("unknown")
                    )
                }
            }
        }
        else {
            val flatRoot = chosenRoot.copy()
            flatRoot.chosenReading = flatRoot.flatReading
            val flatPitches = chosenPitches.map { it.copy() }
            val flatIncrementedPitches: MutableList<Int> = mutableListOf()

            val sharpRoot = chosenRoot.copy()
            sharpRoot.chosenReading = sharpRoot.sharpReading
            val sharpPitches = chosenPitches.map { it.copy() }
            val sharpIncrementedPitches: MutableList<Int> = mutableListOf()

            var numberOfFlats = 0
            var numberOfSharps = 0

            for (pitch in flatPitches) {
                if (pitch.midiValue == flatRoot.midiValue) {
                    pitch.chosenReading = flatRoot.chosenReading

                    if ((pitch.midiValue % 12) !in flatIncrementedPitches) {
                        numberOfFlats++
                        flatIncrementedPitches.add(pitch.midiValue % 12)
                    }
                }
                else {
                    pitch.chosenReading = PitchSpelling(
                        pitch.midiValue % 12,
                        flatRoot.chosenReading.pitchLetter.letterAtInterval(intervals[((pitch.midiValue - flatRoot.midiValue).mod(12))].letterInterval),
                        Accidental("unknown")
                    )
                    if ((pitch.midiValue % 12) !in flatIncrementedPitches) {
                        if (pitch.chosenReading.accidental.type == "flat") {
                            numberOfFlats++
                        }
                        else if (pitch.chosenReading.accidental.type == "doubleflat") {
                            numberOfFlats += 2
                        }
                        flatIncrementedPitches.add(pitch.midiValue % 12)
                    }
                }
            }

            if (!intervals[perfectFifth].inChord && !intervals[diminishedFifth].inChord && !intervals[augmentedFifth].inChord) {
                val impliedFifth: Pitch = Pitch(root.midiValue + 7)

                impliedFifth.chosenReading = PitchSpelling(
                    impliedFifth.midiValue % 12,
                    flatRoot.chosenReading.pitchLetter.letterAtInterval(4),
                    Accidental("unknown")
                )
                when (impliedFifth.chosenReading.accidental.type) {
                    "flat" -> {
                        numberOfFlats++
                    }
                    "doubleflat" -> {
                        numberOfFlats += 2
                    }
                }
            }

            for (pitch in sharpPitches) {
                if (pitch.midiValue == sharpRoot.midiValue) {
                    pitch.chosenReading = sharpRoot.chosenReading

                    if ((pitch.midiValue % 12) !in sharpIncrementedPitches) {
                        numberOfSharps++
                        sharpIncrementedPitches.add(pitch.midiValue % 12)
                    }
                }
                else {
                    pitch.chosenReading = PitchSpelling(
                        pitch.midiValue % 12,
                        sharpRoot.chosenReading.pitchLetter.letterAtInterval(intervals[((pitch.midiValue - sharpRoot.midiValue).mod(12))].letterInterval),
                        Accidental("unknown")
                    )
                    if ((pitch.midiValue % 12) !in sharpIncrementedPitches) {
                        if (pitch.chosenReading.accidental.type == "sharp") {
                            numberOfSharps++
                        }
                        else if (pitch.chosenReading.accidental.type == "doublesharp") {
                            numberOfSharps += 2
                        }
                        sharpIncrementedPitches.add(pitch.midiValue % 12)
                    }
                }
            }

            if (!intervals[perfectFifth].inChord && !intervals[diminishedFifth].inChord && !intervals[augmentedFifth].inChord) {
                val impliedFifth: Pitch = Pitch(root.midiValue + 7)

                impliedFifth.chosenReading = PitchSpelling(
                    impliedFifth.midiValue % 12,
                    sharpRoot.chosenReading.pitchLetter.letterAtInterval(4),
                    Accidental("unknown")
                )
                when (impliedFifth.chosenReading.accidental.type) {
                    "sharp" -> {
                        numberOfSharps++
                    }
                    "doublesharp" -> {
                        numberOfSharps += 2
                    }
                }
            }

            /*

            TODO
                if (numberOfFlats == numberOfSharps) {
                    check if major or minor chord, and if the corresponding scales have more flats or sharps
                }

            TODO
                will have to make scale class, that knows number of flats and sharps
                also have to make chord interpretation tree update a chord quality variable somewhere
                should make Eb minor instead of D# minor

             */

            if (numberOfFlats < numberOfSharps) {
                chosenRoot = flatRoot
                for ((index, pitch) in chosenPitches.withIndex()) {
                    pitch.chosenReading = flatPitches[index].chosenReading
                }
            }
            else {
                chosenRoot = sharpRoot
                for ((index, pitch) in chosenPitches.withIndex()) {
                    pitch.chosenReading = sharpPitches[index].chosenReading
                }
            }
        }

        // combine chosenRoot and chordType to get the name of the chord
        chordName = chosenRoot.chosenReading.name + chordType
    }
}


