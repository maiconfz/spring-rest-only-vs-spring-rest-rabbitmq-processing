import '../scss/styles.scss';

import $ from 'jquery';
import { drawSquare } from './draw-square';
import { SpringRestOnlyParallelProcessor } from './processor/SpringRestOnlyParallelProcessor';
import { SpringRestOnlyProcessor } from './processor/SpringRestOnlyProcessor';

window.jQuery = window.$ = $;

$(() => {

  let inputs = {
    $requestsNumber: $('input#requests-number'),
    $parallelProcessingNumber: $('input#requests-parallel-number'),
    $endpointUrl: $('input#requests-endpoint')
  }

  let $processingAreas = $('.processing-area');

  drawSquare($processingAreas, inputs.$requestsNumber.val());

  let resetSquares = () => {
    $processingAreas.empty();
    drawSquare($('.processing-area'), inputs.$requestsNumber.val());
  };

  inputs.$requestsNumber.on('change', resetSquares).on('keyup', resetSquares);


  let $btnStart = $('button#btn-start');
  let $btnReset = $('button#btn-reset');

  $btnStart.on('click', (event) => {
    $('button, input').prop('disabled', true);
    resetSquares();

    new SpringRestOnlyProcessor($('#spring-rest-only-processing-area'), inputs.$endpointUrl.val()).process().always(() => {
      new SpringRestOnlyParallelProcessor($('#spring-rest-only-parallel-processing-area'), Number.parseInt(inputs.$parallelProcessingNumber.val()), inputs.$endpointUrl.val()).process().always(() => {
        $('button, input').prop('disabled', false);
      });
    });
  });

  $btnReset.on('click', () => {
    resetSquares();
  })

});

