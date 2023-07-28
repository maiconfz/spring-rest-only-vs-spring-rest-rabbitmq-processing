import $ from "jquery";

export class SpringRestOnlyParallelProcessor {

    #$squareArea;
    #squares;
    #parallelProcessingNumber;
    #endpointUrl;

    constructor($squareArea, parallelProcessingNumber, endpointUrl) {
        this.#$squareArea = $squareArea;
        this.#parallelProcessingNumber = parallelProcessingNumber;
        this.#endpointUrl = endpointUrl;
    }

    process() {
        let $defer = $.Deferred();

        this.#squares = this.#$squareArea.children('.square').get();

        console.debug('SpringRestOnlyParallelProcessor start...');

        for (let i = 0; i < this.#parallelProcessingNumber; i++) {
            let promises = new Array();

            promises.push(this.#processNext());

            $.when(promises).always(() => {
                $defer.resolve();
            });
        }

        return $defer.promise();
    }

    #processNext() {
        let $defer = $.Deferred();

        let requestSquare = this.#squares.shift();

        if (!requestSquare) {
            return $defer.resolve().promise();
        }

        let $requestSquare = $(requestSquare);

        $requestSquare.addClass('processing');

        $.post(this.#endpointUrl, () => {
            $requestSquare.removeClass('processing').addClass('success');
        }).fail(() => {
            $requestSquare.removeClass('processing').addClass('fail');
        }).always(() => {
            this.#processNext().always(() => {
                $defer.resolve();
            });
        });;

        return $defer.promise();
    }
}