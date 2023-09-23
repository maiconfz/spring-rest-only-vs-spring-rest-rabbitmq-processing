import $ from "jquery";

export class SpringRestOnlyParallelLimitedProcessor {

    $processingArea;
    #$squareArea;
    #squares;
    #parallelProcessingNumber;
    #endpointUrl;

    startDate;
    endDate;
    timeSpent;

    constructor($processingArea, parallelProcessingNumber, endpointUrl) {
        this.$processingArea = $processingArea;
        this.#$squareArea = this.$processingArea.children('.squares');
        this.#parallelProcessingNumber = parallelProcessingNumber;
        this.#endpointUrl = endpointUrl;
    }

    async process() {
        this.startDate = new Date();

        this.#squares = this.#$squareArea.children('.square').get();

        console.debug('SpringRestOnlyParallelProcessor start...');

        let promises = new Array();

        for (let i = 0; i < this.#parallelProcessingNumber; i++) {
            promises.push(this.#processNext());
        }

        await Promise.all(promises);

        this.endDate = new Date();
        this.timeSpent = (this.endDate - this.startDate) / 1000;
    }

    async #processNext() {
        let requestSquare;

        while (requestSquare = this.#squares.shift()) {
            let $requestSquare = $(requestSquare);

            $requestSquare.addClass('processing');

            await new Promise((resolve) => {
                $.post({
                    url: this.#endpointUrl,
                    data: JSON.stringify({ data: JSON.stringify({ storedNumer: Math.floor(Math.random() * 999999999) }) }),
                    contentType: 'application/json; charset=utf-8',
                    dataType: 'json',
                    success: () => {
                        $requestSquare.removeClass('processing').addClass('success');
                    }
                }).fail(() => {
                    $requestSquare.removeClass('processing').addClass('fail');
                }).always(() => {
                    resolve();
                });
            });
        }
    }
}