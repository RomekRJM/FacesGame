# FacesGame
Android game, where you have to match photo of a person with nationality.


## I want to play!

For best game experience, always checkout most recent tag. Master will compile, but is not guaranteed to be stable.


## Changes history

**v0.5 - 06.05.2015**

Enhancements:

* Added lives. Game is over, when 3 wrong answers given.
* Question generation is now done on separate thread, one by one upon answering, instead of
generating all at the beginning. This results in 7x shorter waiting time, when game loads.
* Prettified scores. They are now animated upon change.
Different animation for correct and wrong answer.
* Loading screen appears after choosing New Game.
* The same person will not be shown again in single game.

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


## Special thanks to:

1. Wikimedia Commons, for beeing great source of free images. All photos in game are CC licensed and downloaded from wikipedia.org.
2. http://artdesigncat.com/ - for creating great blue faces icons, that I used for achievements.
3. http://sekkyumu.deviantart.com/ - for additional icons.
4. Creator and all contributors of https://github.com/daimajia/AndroidViewAnimations library, for their awesome work.
Game uses it to animate scores.
5. My wife Sabina, for understanding and support :D