# tree-sitter-reimu

Reimu grammar for [tree-sitter](https://github.com/tree-sitter/tree-sitter)

## Installing in Neovim

Download the tree-sitter cli tool. You can find binaries [here](https://github.com/tree-sitter/tree-sitter/releases/). Or see the [installation guide](https://tree-sitter.github.io/tree-sitter/creating-parsers#installation).

Generate the grammar:
```sh
tree-sitter generate
```

Test the grammar:
```sh
tree-sitter test
```

Build the grammar:
```sh
tree-sitter build
```

Make sure you have the plugin [tree-sitter](https://github.com/nvim-treesitter/nvim-treesitter).

Now in your Neovim init.lua or wherever you want add the following:
```lua

local parser_config = require("nvim-treesitter.parsers").get_parser_configs()

parser_config.reimu = {
    install_info = {

        -- local path or git repo
        url = "path/to/folder/which/contains/grammar.js/",

        -- note that some parsers also require src/scanner.c or src/scanner.cc
        files = {"src/parser.c"},


        --
        -- optional entries:
        --

        -- default branch in case of git repo if different from master
        branch = "main",

        -- if stand-alone parser without npm dependencies
        generate_requires_npm = false,

        -- if folder contains pre-generated src/parser.c
        requires_generate_from_grammar = false,
    },

    -- if filetype does not match the parser name
    filetype = "rei",
}

-- you can register the file type with neovim here
vim.filetype.add({
    extension = {
        rei = "reimu",
    },
})
```

Once you've added the above lua code you can then install the grammar using:
```
:TSInstall reimu
```

Once it is installed you will want to enable the highlighting. This can be done by copying the contents of `./queries` to your nvim-treesitter [runtime folder](https://github.com/nvim-treesitter/nvim-treesitter?tab=readme-ov-file#adding-queries).

For Lazy.nvim you will want to put the highlights file at `~/.local/share/nvim/lazy/nvim-treesitter/queries/reimu/highlights.scm`


# References

https://www.jonashietala.se/blog/2024/03/19/lets_create_a_tree-sitter_grammar/
