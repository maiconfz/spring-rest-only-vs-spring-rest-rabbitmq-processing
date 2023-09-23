import $ from "jquery";

export class SpringRestOnlyProcessor {

    #$squareArea;
    #squares;
    #endpointUrl;

    constructor($squareArea, endpointUrl) {
        this.#$squareArea = $squareArea;
        this.#endpointUrl = endpointUrl;
    }

    async process() {
        this.#squares = this.#$squareArea.children('.square').get();

        console.debug('SpringRestOnlyProcessor start...');
        return this.#processSquares();
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