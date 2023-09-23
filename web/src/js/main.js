import '../scss/styles.scss';

import $ from 'jquery';
import { drawSquare } from './draw-square';
import { SpringRestOnlyParallelLimitedProcessor } from './processor/SpringRestOnlyParallelLimitedProcessor';
import { SpringRestOnlyParallelUnlimitedProcessor } from './processor/SpringRestOnlyParallelUnlimitedProcessor';
import { SpringRestOnlySequentialProcessor } from './processor/SpringRestOnlySequentialProcessor';


window.jQuery = window.$ = $;

$(() => {
  (async () => {
    let inputs = {
      $requestsNumber: $('input#requests-number'),
      $parallelProcessingNumber: $('input#requests-parallel-number'),
      $endpointUrl: $('input#requests-endpoint')
    }

    let $squareAreas = $('.squares');

    drawSquare($squareAreas, inputs.$requestsNumber.val());

    let resetSquares = () => {
      $squareAreas.empty();
      drawSquare($squareAreas, inputs.$requestsNumber.val());
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

      let springRestOnlySequentialProcessor = new SpringRestOnlySequentialProcessor($('#spring-rest-only-sequential-processing-area.processing-area'), inputs.$endpointUrl.val());
      let springRestOnlyParallelLimitedProcessor = new SpringRestOnlyParallelLimitedProcessor($('#spring-rest-only-parallel-processing-limited-area.processing-area'), Number.parseInt(inputs.$parallelProcessingNumber.val()), inputs.$endpointUrl.val());
      let springRestOnlyParallelUnlimitedProcessor = new SpringRestOnlyParallelUnlimitedProcessor($('#spring-rest-only-parallel-processing-unlimited-area.processing-area'), inputs.$endpointUrl.val());

      springRestOnlySequentialProcessor.process().finally(() => {

        console.log(`Time spent on springRestOnlySequentialProcessor: ${springRestOnlySequentialProcessor.timeSpent}s`);

        springRestOnlyParallelLimitedProcessor.process().finally(() => {

          console.log(`Time spent on springRestOnlyParallelLimitedProcessor: ${springRestOnlyParallelLimitedProcessor.timeSpent}s`);

          springRestOnlyParallelUnlimitedProcessor.process().finally(() => {

            console.log(`Time spent on springRestOnlyParallelUnlimitedProcessor: ${springRestOnlyParallelUnlimitedProcessor.timeSpent}s`);

            $('button, input').prop('disabled', false);
          });

        });

      });

    });

    $btnReset.on('click', () => {
      resetSquares();
    })
  })();
});

