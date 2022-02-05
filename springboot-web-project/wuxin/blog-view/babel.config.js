module.exports = {
    presets: [
        '@vue/cli-plugin-babel/preset'
    ],
    plugins: [
        ["prismjs", {
            "languages":
                [
                    "javascript",
                    "css",
                    "markup",
                    "java",
                    "powershell",
                    "properties",
                    "ini",
                    "git",
                    "csv",
                ],
            "plugins": [
                "line-numbers",
                "copy-to-clipboard",
                "highlight-keywords",
                "jsonp-highlight",
            ],
            "theme": "okaidia",
            "css": true
        }]
    ]
}
