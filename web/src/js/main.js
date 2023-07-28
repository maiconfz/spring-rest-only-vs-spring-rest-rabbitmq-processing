import '../scss/styles.scss';

import $ from 'jquery';
import { drawSquare } from './draw-square';
import { SpringRestOnlyProcessor } from './processor/SpringRestOnlyProcessor';

window.jQuery = window.$ = $;

$(() => {

  let $requestsNumber = $('input#requests-number');
  let $processingAreas = $('.processing-area');

  drawSquare($processingAreas, $requestsNumber.val());

  let resetSquares = () => {
    $processingAreas.empty();
    drawSquare($('.processing-area'), $requestsNumber.val());
  };

  $requestsNumber.on('change', resetSquares).on('keyup', resetSquares);


  let $btnStart = $('button#btn-start');
  let $btnReset = $('button#btn-reset');

  $btnStart.on('click', (event) => {
    $('button, input').prop('disabled', true);
    resetSquares();

    new SpringRestOnlyProcessor($('#spring-rest-only-processing-area')).process().always(() => {
      $('button, input').prop('disabled', false);
    });
  });

  $btnReset.on('click', () => {
    resetSquares();
  })

});

