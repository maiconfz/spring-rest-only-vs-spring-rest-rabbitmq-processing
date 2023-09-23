import '../scss/styles.scss';

import $ from 'jquery';
import { drawSquare } from './draw-square';
import { SpringRestOnlyParallelLimitedProcessor } from './processor/SpringRestOnlyParallelLimitedProcessor';
import { SpringRestOnlyProcessor } from './processor/SpringRestOnlyProcessor';
import { SpringRestOnlyParallelUnlimitedProcessor } from './processor/SpringRestOnlyParallelUnlimitedProcessor';


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

    $.post({
      url: inputs.$endpointUrl.val(),
      data: JSON.stringify({}),
      contentType: 'application/json; charset=utf-8',
      dataType: 'json'
    })

    new SpringRestOnlyProcessor($('#spring-rest-only-processing-area'), inputs.$endpointUrl.val()).process().finally(() => {
      new SpringRestOnlyParallelLimitedProcessor($('#spring-rest-only-parallel-processing-limited-area'), Number.parseInt(inputs.$parallelProcessingNumber.val()), inputs.$endpointUrl.val()).process().always(() => {
        new SpringRestOnlyParallelUnlimitedProcessor($('#spring-rest-only-parallel-processing-unlimited-area'), inputs.$endpointUrl.val()).process().always(() => {
          $('button, input').prop('disabled', false);
        });
      });
    });
  });

  $btnReset.on('click', () => {
    resetSquares();
  })

});

