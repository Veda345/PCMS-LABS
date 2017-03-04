$all = "";
 
foreach $cur (<>) {
    $cur =~ s/\s+/\ /g;
    $cur =~ s/^\s+(.*?)\s+&/$1/g;
    $cur =~ s/^\s+//g;
    $cur =~ s/\s+$//g;
    $all = $all.$cur."\n";
}
 
$all =~ s/^\s*\n/\n/g;
$all =~ s/\n\s*\n/\n\n/g;
$all =~ s/\n\s*&/\n/g;
$all =~ s/^\n+//g;
$all =~ s/\n{2,}/\n\n/g;
$all =~ s/\n+$//g;
 
print $all;