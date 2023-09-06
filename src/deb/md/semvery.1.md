% SEMVERY(1) github.com/siakhooi | JavaCli Commands
% Siak Hooi
% June 2023

# NAME
semvery - semver utilities.

# SYNOPSIS
**semvery** [*OPTION*]

# DESCRIPTION
semver utils.\
\ \
\ \ \ -h, --help\
\ \ \ \ \ \ Print command-line options.\
\ \
\ \ \ -v, --version\
\ \ \ \ \ \ Print semvery version.\
\ \
\ \ \ -o, --operation operator value \[values...\]\
\ \ \ \ \ \ Operation, valid operators are **isValid**, **isStable**, **isGreater**, **isLower**, **isEqual**\
\ \
\ \ \ -r refValue\
\ \ \ \ \ \ reference value, valid operator is **isGreater**, **isLower**, **isEqual**

# EXAMPLES

\ \ \ semvery -o isValid 1.0.0\
\ \
\ \ \ semvery -o isValid ABC\
\ \
\ \ \ semvery -o isValid 1.0.0 2.0.0\
\ \
\ \ \ semvery -o isStable 1.0.0 2.0.0\
\ \
\ \ \ semvery -o isGreater -r 1.5.0 1.0.0 2.0.0\
\ \
\ \ \ semvery -o isLower -r 1.5.0 1.0.0 2.0.0\
\ \
\ \ \ semvery -o , isEqual -r 1.5.0 1.5.0+3 2.0.0\
\ \

# LICENSE
MIT

# BUGS
Report bugs at https://github.com/siakhooi/javacli-semvery/issues.
