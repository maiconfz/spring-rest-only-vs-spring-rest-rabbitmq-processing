import $ from 'jquery'

export function drawSquare($el, quantity = 160) {
    for(let i = 0; i< quantity; i++) {
        $el.append(`<span class="square"></span>`);
    }
}