# JMP
As the highly informative mini-description said, this is an assembly language VM with too many bells and whistles, all of them purely visual and intended to cover up the fact that I don't really know assembly.

# Tell me more. 

There are `1024` RAM cells, each with possible values from `0000` to `ffff`. (All unsigned for now.)

Currently I've got `MOV`, `ADD`, `CMP` and `JMP`(s) working. No labels yet, unfortunately; you'll have to specify the argument to `JMP` as a number. Also, stack ops haven't been implemented yet.

This screenshot of an in-progress program brought to you by PicPick. (It's Fibonacci, of course. Don't ask.)

![text](http://i.imgur.com/OKSYp0f.png)

Features:

* variable clock speed
* extensive exception-handling abuse
* tooltips on the RAM cells tell you the address and the opcode (if any) stored in each cell
* more to come soon

# Credits

This is highly influenced by (almost a Java port of) [schweigi/assembler-simulator](https://schweigi.github.io/assembler-simulator/).

# License

MIT. If you really want to read it, here it is:

The MIT License (MIT)

Copyright (c) 2015 Soham Chowdhury aka mrkgnao

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
