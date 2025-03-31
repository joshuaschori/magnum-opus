Magnum Opus is a Kotlin Multiplatform app. The app is a tool for guitarists that is able to identify chord 
shapes, display scales, and display voicing options for chords using a fretboard interface. The 
fretboard can be tuned to any tuning, can have a variable number of strings and frets, and provides 
contextual information about the function of the pitches.

* `Chord Identification` allows you to tap on the frets of each of the strings and then 
displays the most likely reading of the chord implied by the fretted notes. Tap the \"Alternative 
Readings\" button to view and select other possible readings of the chord based on each of the 
other notes as the root.

* `Interval Display` takes a root note and displays intervals from that note in every octave 
across the fretboard. Select the intervals for a scale to view it in the selected tuning, or select 
the intervals that make up a chord to view all of the possible notes within that chord and inform 
your decision of how to voice that chord.

* `Settings` sets up your guitar to have between 4 and 8 strings of any tuning and 
between 12 and 24 frets. Select which page you would like to view by default upon opening the app.

The app was initially made as a project for Harvard Online's CS50 class and was a first attempt at building a complete android application from scratch. The following video has a demo of the version submitted for the class: https://www.youtube.com/watch?v=pM8rKoxoiqI

I'm planning on restructuring the app after finishing other projects. I also have more functionality I am interested in adding, and I would love to someday make the chord identification into a standalone library for others to use, after tweaking the numbers that determine how the function chooses which of the possible chord interpretations is the most likely / relevant.
