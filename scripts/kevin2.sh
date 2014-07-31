# script by Kevin Humphries

gsed -E "
s/\{\{multistub[^]}]*\]\]// # error case
s/\{\{scroll box.*// # error case
s/\[\[[^]]+\|/[[/g # remove file details and all but last of | lists
s/\[\[//g;s/\]\]//g # remove remaining links
s/\{\{flag\|([^}]*)\}\}/\1/ # keep country names
s/\{\{[^}]*\}\}||//g # remove single line {{...}} blocks
s/&lt;[^&]*&gt;//g
s/[']+/'/g # normalize quotes
s/^(<(id|title)) [^>]+/\1/
s|^=====|<subsubsubsection>|;s|===== *$|</subsubsubsection>|
s|^====|<subsubsection>|;s|==== *$|</subsubsection>|
s|^===|<subsection>|;s|=== *$|</subsection>|
s|^==|<section>|;s|== *$|</section>|
s|^([	])*:|\1	|g
" | \
gsed -E "
s/\{\{[^}]*//
s/\|[^}]*(\}\})?//g
s/^ *\}\}//
s/^!.*//
s/^[{}]//
"
