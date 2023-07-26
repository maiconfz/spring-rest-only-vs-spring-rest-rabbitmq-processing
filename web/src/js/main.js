import '../scss/styles.scss';

import $ from 'jquery';
import { drawSquare } from './draw-square';

window.jQuery = window.$ = $;

let $requestsNumber = $('input#requests-number');
let $processingAreas = $('.processing-area');

drawSquare($processingAreas, $requestsNumber.val());

let requestsNumberChangeHandler = () =>  {
  $processingAreas.empty();
  drawSquare($('.processing-area'), $requestsNumber.val());
}

$requestsNumber.on('change', requestsNumberChangeHandler);