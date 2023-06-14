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
\ \ \ -o, --operation operator value\
\ \ \ \ \ \ Operation, valid operator is **isValid**

# EXAMPLES

\ \ \ semvery -o isValid 1.0.0\
\ \ \ \ \ \ valid\
\ \
\ \ \ semvery -o isValid ABC\
\ \ \ \ \ \ invalid

# LICENSE
MIT

# BUGS
Report bugs at https://github.com/siakhooi/javacli-semvery/issues.
