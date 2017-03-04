while (<>) {
    print if /\b(?P<part>.+)(?P=part)\b/;
}