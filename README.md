# JMP
An assembly language VM with a bit more visual feedback than is necessary

# What is this?
Well, you read the subtitle. 

There are `1024` RAM cells, each with possible values from `0000` to `ffff`. (All unsigned for now.)

Currently I've got `MOV`, `ADD`, `CMP` and `JMP`(s) working, although only when the code is supplied as a list in code. (So there is no parsing).

Here's a picture of an in-progress program that calculates Fibonacci programs in memory cells.

![Here's a picture.](http://i.imgur.com/v1l2q6I.png)

# Credits

This is highly influenced by (almost a Java port of) [schweigi/assembler-simulator](https://schweigi.github.io/assembler-simulator/).

# License

MIT. If you really want to read it, here it is:

The MIT License (MIT)

Copyright (c) 2015 Soham Chowdhury aka mrkgnao

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
