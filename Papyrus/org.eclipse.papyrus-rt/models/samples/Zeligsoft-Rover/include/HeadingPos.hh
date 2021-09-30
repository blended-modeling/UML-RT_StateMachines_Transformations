#ifndef HEADINPOS_HH
#define HEADINPOS_HH

/**
 * Direction command for Drive
 */
enum DirectionCmd {
	Forward,
	Backward,
	TurnLeft,
	TurnRight,
	Stop
};
std::ostream& operator<<(std::ostream& os, DirectionCmd dc)
{
    switch(dc) {
         case Forward	: os << "Forward";    break;
         case Backward	: os << "Backward"; break;
         case TurnLeft	: os << "TurnLeft";  break;
         case TurnRight	: os << "TurnRight";   break;
         case Stop		: os << "Stop";   break;
         default : os.setstate(std::ios_base::failbit);
    }
    return os;
}

/**
 * Rover sides
 */
enum RoverSides {
	Front,
	Right,
	Back,
	Left
};
std::ostream& operator<<(std::ostream& os, RoverSides rs)
{
    switch(d) {
         case Front	: os << "Front";    break;
         case Right	: os << "Right"; break;
         case Back	: os << "Back";  break;
         case Left	: os << "Left";   break;
         default : os.setstate(std::ios_base::failbit);
    }
    return os;
}


/**
 * Map headings
 */
enum Headings {
	North,
	East,
	South,
	West
};
std::ostream& operator<<(std::ostream& os, Headings h)
{
    switch(h) {
         case North	: os << "North";    break;
         case East	: os << "East"; break;
         case South	: os << "South";  break;
         case West	: os << "West";   break;
         default : os.setstate(std::ios_base::failbit);
    }
    return os;
}


/**
 *  Side sensors headings based on rover headingx
 */
const int HEADINGPOS [4] [4] = {	/* [Headings] [RoverSides:Front, Right,	Back, Left] */
			   /* Front,			Right, 				Back,  				Left */
	/* North */	{ Headings::North,	Headings::East,		Headings::South,	Headings::West }
	/* East  */	{ Headings::East,	Headings::South,	Headings::West,		Headings::North }
	/* South */	{ Headings::South,	Headings::West,		Headings::North,	Headings::East }
	/* West  */	{ Headings::West,	Headings::North,	Headings::East,		Headings::South }
};

#endif
