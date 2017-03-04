while (<>) {
    s/(?P<part>\w+)(?P=part)/$1/g;
    print ;
}