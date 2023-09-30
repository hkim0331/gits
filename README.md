# gits

## SYNOPSIS

gits [options] [something] [dir]

dir 内の git dirs に対し、git something を実行する。

* --paralell or -p で並列実行、
* --serial or -s で逐次実行。

デフォルトは、

gits --parallel status .

## examples

~/projects 内の複数の git フォルダの状態を並列にチェックする。

    % gits --paralell status ~/projects

作業ディレクトリ中の複数の git フォルダを逐次 fetch する。

    % gits -s fetch .

作業ディレクトリ中の複数の git フォルダを並列に pull する。

    % gits -p pull
