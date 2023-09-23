import $ from "jquery";

export class SpringRestOnlySequentialProcessor {

    $processingArea;
    #$squareArea;
    #squares;
    #endpointUrl;

    startDate;
    endDate;
    timeSpent;

    constructor($processingArea, endpointUrl) {
        this.$processingArea = $processingArea;
        this.#$squareArea = this.$processingArea.children('.squares');
        this.#endpointUrl = endpointUrl;
    }

    async process() {
        this.startDate = new Date();

        this.#squares = this.#$squareArea.children('.square').get();

        console.debug('SpringRestOnlyProcessor start...');

        await this.#processSquares();

        this.endDate = new Date();
        this.timeSpent = (this.endDate - this.startDate) / 1000;
    }

    async #processSquares() {
        let requestSquare;

        while (requestSquare = this.#squares.shift()) {
            console.debug('Processing', requestSquare);
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