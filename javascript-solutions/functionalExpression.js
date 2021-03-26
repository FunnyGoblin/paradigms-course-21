'use strict';

const process = (f, arr, neutral) => {
    for(const elem of arr){
        neutral = f(neutral, elem);
    }
    return neutral;
}

const cnst = value => (...args) => value;
const variable = (name) => (...args) => args[VARS[name]];
const add = (op1, op2) => (...args) => op1(...args) + op2(...args);
const subtract = (op1, op2) => (...args) => op1(...args) - op2(...args);
const multiply = (op1, op2) => (...args) => op1(...args) * op2(...args);
const divide = (op1, op2) => (...args) => op2(...args) == 0 ? Infinity : op1(...args) / op2(...args);
const negate = (op) => (...args) => -op(...args);
const one = (...args) => 1;
const two = (...args) => 2;
const min5 = (op1, op2, op3, op4, op5) => (...args) => process(Math.min, [op1(...args)
    , op2(...args)
    , op3(...args)
    , op4(...args)
    , op5(...args)]
    , Infinity)
const max3 = (op1, op2, op3) => (...args) => process(Math.max, [op1(...args), op2(...args), op3(...args)], -Infinity)

const OPS = {
    '*': [multiply, 2],
    '+': [add, 2],
    '-': [subtract, 2],
    '/': [divide, 2],
    'negate': [negate, 1],
    'max3': [max3, 3],
    'min5': [min5, 5]
};

const CONSTS = {
    'one': one,
    'two':two
};

const VARS = {
    'x':0,
    'y':1,
    'z':2
};

const parse = expression => {
    const tokens = expression.split(' ').filter(element => element.length !== 0);
    let stack = [];
    for(const token of tokens){
        //println('\''+ token + '\'');
        if(token in OPS){
            const op = OPS[token];
            let args = [];
            for(let i = 0; i < op[1]; i++){
                args.unshift(stack.pop());
            }
            stack.push(op[0](...args));
        }
        else if(token in VARS){
            stack.push(variable(token));
        }
        else if(token in CONSTS){
            stack.push(CONSTS[token]);
        }
        else{
            stack.push(cnst(parseInt(token)));
        }
    }
    //println(stack);
    return stack.pop();
}