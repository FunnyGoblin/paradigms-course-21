'use strict';


const cnst = value => _ => value;
const variable = (name) => (...args) => args[VARS[name]];

const operation = (f, ...args) => (...vars) =>{
    let res = args[0](...vars);
    for(let i = 1; i < args.length; i++){
        res = f(res, args[i](...vars));
    }
    return res;
}
// :NOTE: еще больше общего кода вынести
const add = (...operands) => operation((a, b) => a + b, ...operands);
const subtract = (...operands) => operation((a, b) => a - b, ...operands);
const multiply = (...operands) => operation((a, b) => a * b, ...operands);
const divide = (...operands) => operation((a, b) => a / b, ...operands);
const negate = operand => operation(a => -a, operand, operand);
const min5 = (...operands) => operation(Math.min, ...operands);
const max3 = (...operands) => operation(Math.max, ...operands);

const one = cnst(1);
const two = cnst(2);

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
    let stack = [];
    for(const token of expression.trim().split(/\s+/)){
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
    return stack.pop();
}