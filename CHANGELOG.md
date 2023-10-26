# gits.clj

## Unreleased
- `gits pull .`  を `gits pull` と間違うこと(. を忘れる)が多い。
- `gits pull *` が正しいコマンドラインではないか？2023-10-14
- gits が正常終了しなかったのはどのディレクトリか？

## 0.2.0-snapshot
- log


## 0.1.5 - 2023-10-02
- ヘルプが gits.cj のままなのを修正した。

## 0.1.4 - 2023-10-02
- added bump-version.sh
- インストール先スクリプト名から .clj をとって、gits とした。

## 0.1.2 - 2023-10-01
- add an example, `gits.clj`

## 0.1.1 - 2023-10-01
- git flow init
- git-dir?
- git-dirs
- usage
- Makefile (to ~/bin/gits.clj)

## 0.1.0 - 2023-10-01
- initialized repository

    git init
    gh repo create hkim0331/gits --public
    git remote add ssh://git@github.com/hkim0331/gits.git
