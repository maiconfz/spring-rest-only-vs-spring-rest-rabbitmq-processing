import $ from "jquery";

export class SpringRestOnlyParallelUnlimitedProcessor {

    #$squareArea;
    #endpointUrl;

    constructor($squareArea, endpointUrl) {
        this.#$squareArea = $squareArea;
        this.#endpointUrl = endpointUrl;
    }

    process() {
        let $squares = this.#$squareArea.children('.square');

        console.debug('SpringRestOnlyParallelUnlimitedProcessor start...');

        let promises = new Array();

        $squares.each((i, square) => {
            promises.push(this.#processSquare($(square)));
        });

        return $.when(...promises).promise();
    }

    #processSquare($square) {
        let $defer = $.Deferred();

        $square.addClass('processing');

        $.post({
            url: this.#endpointUrl,
            data: JSON.stringify({ data: JSON.stringify({ storedNumer: Math.floor(Math.random() * 999999999) }) }),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: () => {
                $square.removeClass('processing').addClass('success');
            }
        }).fail(() => {
            $square.removeClass('processing').addClass('fail');
        }).always(() => {
            $defer.resolve();
        });

        return $defer.promise();
    }
}