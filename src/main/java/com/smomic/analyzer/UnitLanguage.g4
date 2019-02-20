grammar UnitLanguage;

options {
  language = Java;
}

/**
 *** Parser rules ***
 */

program
:
	importDirective* funDefinition*
;

importDirective
:
	Import (Id
           	| LeftBracket importDefinition RightBracket) Semi
;

importDefinition
:
	Id (Colon Id)* importContent+
;

importContent
:
	Line Id Colon Number
;

funDefinition
:
	Function Id LeftParen parameters? RightParen body
;
	
parameters
:
	Id (Coma Id)*
;

body
:
	LeftBrace statement* RightBrace
;

statement
:
	ifStatement
	| loopStatement
	| returnStatement Semi
	| assignStatement Semi
	| funCall Semi
;

ifStatement
:
	If LeftParen condition RightParen body (Else body)?
;

loopStatement
:
	Loop LeftParen condition RightParen body
;

returnStatement
:
	Return expression
;

assignStatement
:
	Id assignmentOp expression
;

expression
:
	multiExp (addOp multiExp)*
;

multiExp
:
	unaryOp? simpleExp (multiOp simpleExp)*
;

simpleExp
:
	primaryExp convertOp?
;

primaryExp
:
	(LeftParen expression RightParen)
	| Id
	| literal
	| funCall
;

funCall
:
	Id LeftParen arguments? RightParen
;

arguments
:
	expression (Coma expression)*
;

condition
:
	andCond (orOp andCond)*
;

andCond
:
	comparisonCond (andOp comparisonCond)*
;

comparisonCond
:
	primaryCond (comparisonOp primaryCond)?
;

primaryCond
:
	notOp? (parentCond
			| Id
			| literal
			| funCall)
;

parentCond
:
	LeftParen condition RightParen
;

addOp
:
	Plus
	| unaryOp
;

unaryOp
:
	Minus
;

multiOp
:
	Multiply
	| Divide
	| Modulo
;

assignmentOp
:
	Assignment
;

notOp
:
	Negation
;

orOp
:
	Or
;

andOp
:
	And
;

comparisonOp
:
	Equality
	| Inequality
	| Greater
	| Less
	| GreaterEqual
	| LessEqual
;

convertOp
:
	Dot Id
;

literal
:
	Number Id?
;

	
	
/**
 *** Lexer rules ***
 */	
	
Import
:
	'import'
;

Function			
: 
	'function'
;

LeftParen	
: 
	'('
;

RightParen	
: 
	')'
;

LeftBracket
:
	'['
;

RightBracket			
: 
	']'
;

LeftBrace
: 
	'{'
;

RightBrace			
: 
	'}'
;

Colon				
: 
	':'
;

Line				
: 
	'|'
;

Coma				
: 
	','
;

Semi
: 
	';'
;

Dot					
:
	'.'
;

If					
: 
	'if'
;

Else				
: 
	'else'
;

Loop				
: 
	'loop'
;

Return				
: 
	'return'
;

Plus				
: 
	'+'
;

Minus				
: 
	'-'
;

Divide				
: 
	'/'
;

Multiply			
: 
	'*'
;

Modulo				
: 
	'%'
;

Assignment			
: 
	'='
;

Negation			
: 
	'!'
;

Or					
: 
	'||'
;

And					
: 
	'&&'
;

Equality			
: 
	'=='
;

Inequality			
: 
	'!='
;

Less				
: 
	'<'
;

LessEqual			
: 
	'<='
;

Greater				
: 
	'>'
;

GreaterEqual
: 
	'>='
;

fragment
NONDIGIT
:
	[a-zA-Z_]
;

fragment
DIGIT
:
	[0-9]
;

Number
:
	DIGIT+ (Dot DIGIT+)?
;

Id
:
	NONDIGIT
	(
		NONDIGIT
		| DIGIT
	)*
;

Whitespace
:
	[ \t\r\n]+ -> skip 
;
