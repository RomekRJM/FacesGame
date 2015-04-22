# FacesGame
Android game, where you have to match photo of a person with nationality.


## I want to play!

For best game experience, always checkout most recent tag. Master will compile, but is not guaranteed to be stable.


## Changes history

**v0.4 - 22.04.2015**

Enhancements:

* Added achievements subsystem backed up by sqlite.
* New view: Collectable, for displaying detailed info about achievements, using funny faces.
* Implemented logic for following achievements: "Guess x in a row", "Have total x correct guesses",
"Complete x game", "Reach score x", "Gather x points in any number of games"
* Achievements shown on end game screen.

**v0.3 - 04.04.2015**

Enhancements:

* Added missing countries and photos: Abkhazia, Somaliland, Kosovo, Vatican City, 
  Transnistria, South Ossetia, Nagorno-Karabakh, Maldives, Saint Lucia, Norfolk Island.
* Implemented storage of scores in sqlite and top scores view.
* Difficulty level grows every few question: less time, more possible answers, 
  countries closer to each other.
  
Bug fixes:

* End game buttons now visible on landscape mode.
* Corrected behaviour of faces_algorithm library, for randomizing close countries.  


**v0.2 - 25.03.2015**

Enhancements:

* Implemented timeout, marked by timer bar, that smoothly changes colors, from green to red, when time passes.
* More sophisticated method, for computing and displaying score, based on how quick answer was given and answering spree multiplier.
* Portrait flashes changing color to red/green with check/cross mark, when correct/wrong answer given.
* User has to press a button to go to next question.


**v0.1 - 16.03.2015**

* Initial, somehow playable version.
