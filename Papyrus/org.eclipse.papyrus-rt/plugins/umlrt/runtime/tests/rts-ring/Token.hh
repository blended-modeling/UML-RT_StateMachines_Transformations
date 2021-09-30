
#ifndef TOKEN_HH
#define TOKEN_HH

struct UMLRTObject_class;

class Token
{
public:
    unsigned int data;
    unsigned int label;
    static unsigned int Label;
    void write( char id );
};
extern const UMLRTObject_class UMLRTType_Token;

#endif

