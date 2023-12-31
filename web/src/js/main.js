import '../scss/styles.scss';

import $ from 'jquery';
import { drawSquare } from './draw-square';
import { SpringRestOnlyAllInOneProcessor } from './processor/SpringRestOnlyAllInOneProcessor';
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

    let hideTimeSpentText = (hide = true) => {
      $('.time-spent-text').prop('hidden', hide);
    }

    let resetProcessingArea = () => {
      $squareAreas.empty();
      drawSquare($squareAreas, inputs.$requestsNumber.val());
      hideTimeSpentText();
    };

    inputs.$requestsNumber.on('change', resetProcessingArea).on('keyup', resetProcessingArea);


    let $btnStart = $('button#btn-start');
    let $btnReset = $('button#btn-reset');

    $btnStart.on('click', (event) => {
      $('button, input').prop('disabled', true);
      resetProcessingArea();

      $.post({
        url: inputs.$endpointUrl.val(),
        data: JSON.stringify({}),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json'
      })

      let springRestOnlySequentialProcessor = new SpringRestOnlySequentialProcessor($('#spring-rest-only-sequential-processing-area.processing-area'), inputs.$endpointUrl.val());
      let springRestOnlyParallelLimitedProcessor = new SpringRestOnlyParallelLimitedProcessor($('#spring-rest-only-parallel-processing-limited-area.processing-area'), Number.parseInt(inputs.$parallelProcessingNumber.val()), inputs.$endpointUrl.val());
      let springRestOnlyParallelUnlimitedProcessor = new SpringRestOnlyParallelUnlimitedProcessor($('#spring-rest-only-parallel-processing-unlimited-area.processing-area'), inputs.$endpointUrl.val());
      let springRestOnlyAllInOneProcessor = new SpringRestOnlyAllInOneProcessor($('#spring-rest-only-all-in-one-area'), `${inputs.$endpointUrl.val()}/add-all`);


      springRestOnlySequentialProcessor.process().finally(() => {

        console.log(`Time spent on springRestOnlySequentialProcessor: ${springRestOnlySequentialProcessor.timeSpent}s`);
        springRestOnlySequentialProcessor.$processingArea.find('.time-spent-text').prop('hidden', false);
        springRestOnlySequentialProcessor.$processingArea.find('.time-spent').text(`${springRestOnlySequentialProcessor.timeSpent}s`);

        springRestOnlyParallelLimitedProcessor.process().finally(() => {

          console.log(`Time spent on springRestOnlyParallelLimitedProcessor: ${springRestOnlyParallelLimitedProcessor.timeSpent}s`);
          springRestOnlyParallelLimitedProcessor.$processingArea.find('.time-spent-text').prop('hidden', false);
          springRestOnlyParallelLimitedProcessor.$processingArea.find('.time-spent').text(`${springRestOnlyParallelLimitedProcessor.timeSpent}s`);

          springRestOnlyParallelUnlimitedProcessor.process().finally(() => {

            console.log(`Time spent on springRestOnlyParallelUnlimitedProcessor: ${springRestOnlyParallelUnlimitedProcessor.timeSpent}s`);
            springRestOnlyParallelUnlimitedProcessor.$processingArea.find('.time-spent-text').prop('hidden', false);
            springRestOnlyParallelUnlimitedProcessor.$processingArea.find('.time-spent').text(`${springRestOnlyParallelUnlimitedProcessor.timeSpent}s`);

            springRestOnlyAllInOneProcessor.process().finally(() => {

              console.log(`Time spent on springRestOnlyAllInOneProcessor: ${springRestOnlyAllInOneProcessor.timeSpent}s`);
              springRestOnlyAllInOneProcessor.$processingArea.find('.time-spent-text').prop('hidden', false);
              springRestOnlyAllInOneProcessor.$processingArea.find('.time-spent').text(`${springRestOnlyAllInOneProcessor.timeSpent}s`);

              $('button, input').prop('disabled', false);
            });


          });

        });

      });

    });

    $btnReset.on('click', () => {
      resetProcessingArea();
    })
  })();
});

