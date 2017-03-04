$part = qr/\s*[^>]+?=\s*\".*?\"\s*/;
$all = "";
 
foreach $cur (<>) {
    $all = $all.$cur." ";
}
 
@matches = ( $all =~ m/<\s*a\s+${part}*\s*href\s*=\s*"(.*?)"\s*${part}*\s*>/g );
%set = ();
foreach $link (@matches) {
    $link =~ s/.*?:?\/\/(.*)/$1/;
    $link =~ s/(.*?)[\:\/\?#].*/$1/;
 
    if (!(($link =~ /^\./) || ($link =~ /\.$/) || ($link =~ /\.{2,}/))) {
            $set{$link} = 1;
        }
}
 
print "$_\n" for sort(keys %set);