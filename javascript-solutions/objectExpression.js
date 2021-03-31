function Const(value) {
    this.value = value;
}

Const.prototype = {
    evaluate: function () {
        return this.value;
    },
    diff: function () {
        // :NOTE: new Const(0)
        return new Const(0);
    },
    toString: function () {
        return this.value.toString()
    }
}

const VARS = {
    "x": 0,
    "y": 1,
    "z": 2
}

function Variable(name) {
    this.name = name;
}

Variable.prototype = {
    evaluate: function (...args) {
        return args[VARS[this.name]];
    },
    diff: function (difVar) {
        // :NOTE: new Const(0)
        return new Const(difVar === this.name ? 1 : 0);
    },
    toString: function () {
        return this.name;
    }
}

// :NOTE: Обобщить
function Binary(op1, op2, f) {
    this.left = op1;
    this.right = op2;
    this.operation = f;
    this.evaluate = function (...vars) {
        return this.operation(this.left.evaluate(...vars), this.right.evaluate(...vars));
    }
}

function binString(op, sign) {
    return op.left.toString() + " " + op.right.toString() + " " + sign;
}

// :NOTE:  const Add = makeOp('+', (a, b) => a + b, (difVar, left, right) => new Add(left.diff(difVar), right.diff(difVar)))
function Add(op1, op2) {
    Binary.call(this, op1, op2, (a, b) => a + b);
}

Add.prototype = {
    //evaluate: function (...vars) {return process((a, b) => a + b, this.left, this.right)(...vars)},
    diff: function (difVar) {
        return new Add(this.left.diff(difVar), this.right.diff(difVar))
    },
    toString: function () {
        return binString(this, '+')
    }
}

function Subtract(op1, op2) {
    Binary.call(this, op1, op2, (a, b) => a - b);
}

Subtract.prototype = {
    //evaluate: function (...vars) {return process((a, b) => a - b, this.left, this.right)(...vars)},
    diff: function (difVar) {
        return new Subtract(this.left.diff(difVar), this.right.diff(difVar))
    },
    toString: function () {
        return binString(this, '-')
    }
}

function Multiply(op1, op2) {
    Binary.call(this, op1, op2, (a, b) => a * b);
}

Multiply.prototype = {
    //evaluate: function (...vars) {return process((a, b) => a * b, this.left, this.right)(...vars)},
    diff: function (difVar) {
        return new Add(new Multiply(this.left, this.right.diff(difVar)), new Multiply(this.left.diff(difVar), this.right))
    },
    toString: function () {
        return binString(this, '*')
    }
}

function Divide(op1, op2) {
    Binary.call(this, op1, op2, (a, b) => a / b);
}

Divide.prototype = {
    //evaluate: function (...vars) {return process((a, b) => a / b, this.left, this.right)(...vars)},
    diff: function (difVar) {
        let dl = this.left.diff(difVar);
        let dr = this.right.diff(difVar);
        return new Divide(
            new Subtract(
                new Multiply(dl, this.right)
                , new Multiply(this.left, dr)
            )
            , new Multiply(this.right, this.right)
        )
    },
    toString: function () {
        return binString(this, '/')
    }
}

function Negate(op) {
    this.op = op;
}

Negate.prototype = {
    evaluate: function (...vars) {
        return -this.op.evaluate(...vars)
    },
    diff: function (difVar) {
        return new Negate(this.op.diff(difVar))
    },
    toString: function () {
        return this.op.toString() + " negate"
    }
}

function Cube(op) {
    this.op = op;
}
Cube.prototype = {
    evaluate: function (...vars) {
        return this.op.evaluate(...vars) ** 3
    },
    diff: function (difVar) {
        return new Multiply(
            new Const(3),
            new Multiply(
                this.op.diff(difVar),
                new Multiply(
                    this.op,
                    this.op
                )
            )
        )
    },
    toString: function () {
        return this.op.toString() + " cube";
    }
}

function Cbrt(op) {
    this.op = op;
}
Cbrt.prototype = {
    evaluate: function (...vars) {
        return Math.cbrt(this.op.evaluate(...vars))
    },
    diff: function (difVar) {
        return new Multiply(
            new Const(1 / 3),
            new Divide(
                this.op.diff(difVar),
                new Multiply(this, this)
            )
        )
    },
    toString: function () {
        return this.op.toString() + " cbrt";
    }
}

const OPS = {
    // :NOTE:     '*': Multiply
    '*': [Multiply, 2],
    '+': [Add, 2],
    '-': [Subtract, 2],
    '/': [Divide, 2],
    'negate': [Negate, 1],
    'cbrt': [Cbrt, 1],
    'cube': [Cube, 1]
};


const parse = expression => {
    let stack = [];
    for (const token of expression.trim().split(/\s+/)) {
        if (token in OPS) {
            const op = OPS[token];
            // :NOTE: Array.splice
            let args = [];
            for (let i = 0; i < op[1]; i++) {
                args.unshift(stack.pop());
            }
            stack.push(new op[0](...args));
        } else if (token in VARS) {
            stack.push(new Variable(token));
        } else {
            stack.push(new Const(parseInt(token)));
        }
    }
    return stack.pop();
}
