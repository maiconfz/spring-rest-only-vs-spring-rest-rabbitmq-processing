import $ from "jquery";

export class SpringRestOnlyParallelLimitedProcessor {

    #$squareArea;
    #squares;
    #parallelProcessingNumber;
    #endpointUrl;

    constructor($squareArea, parallelProcessingNumber, endpointUrl) {
        this.#$squareArea = $squareArea;
        this.#parallelProcessingNumber = parallelProcessingNumber;
        this.#endpointUrl = endpointUrl;
    }

    async process() {
        this.#squares = this.#$squareArea.children('.square').get();

        console.debug('SpringRestOnlyParallelProcessor start...');

        let promises = new Array();

        for (let i = 0; i < this.#parallelProcessingNumber; i++) {
            promises.push(this.#processNext());
        }

        await Promise.all(promises);
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