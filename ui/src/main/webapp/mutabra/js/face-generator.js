/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

!function ($) {

    /* FACE GENERATOR CLASS DEFINITION
     * ========================= */
    var FaceGenerator = function (element) {
        this.$element = $(element);

        this.part = this.$element.data('part');
        this.value = this.$element.data('value');
        this.$picture = this.$element.find('svg > g');

        var selector = this.$element.data('target');
        this.$target = selector && $(selector).find('g[data-part=' + this.part + ']');
        this.$input = selector && $(selector).find('input[data-part=' + this.part + ']');
    };

    FaceGenerator.prototype = {
        constructor: FaceGenerator,

        show: function () {
            // redraw face with new part
            this.$target && this.$target.empty().append(this.$picture.children().clone());
            // save chosen value
            this.$input && this.$input.val(this.value);

            // activate new menu item
            $('.active[data-toggle=face][data-part=' + this.part + ']').removeClass('active');
            this.$element.addClass('active');
        }
    };

    /* FACE GENERATOR PLUGIN DEFINITION
     * ========================== */
    $.fn.faceGenerator = function (option) {
        return this.each(function () {
            var $this = $(this),
                data = $this.data('faceGenerator');
            if (!data) $this.data('faceGenerator', (data = new FaceGenerator(this)));
            if (typeof option == 'string') data[option]();
        });
    };

    $.fn.faceGenerator.Constructor = FaceGenerator;


    /* FACE TITLE CLASS DEFINITION
     * ========================= */
    var FaceTitle = function (element) {
        this.$element = $(element);

        var selector = this.$element.data('target');
        var part = this.$element.data('part');
        this.$target = selector && $(selector).find('g[data-part=' + part + ']');
        this.$input = selector && $(selector).find('input[data-part=' + part + ']');
    };

    FaceTitle.prototype = {
        constructor: FaceTitle,

        refresh: function () {
            var value = this.$element.val();

            // redraw face with new title
            this.$target && this.$target.find('text').text(value);
            // save chosen value
            this.$input && this.$input.val(value);
        }
    };

    /* FACE TITLE PLUGIN DEFINITION
     * ========================== */
    $.fn.faceTitle = function (option) {
        return this.each(function () {
            var $this = $(this),
                data = $this.data('faceTitle');
            if (!data) $this.data('faceTitle', (data = new FaceTitle(this)));
            if (typeof option == 'string') data[option]();
        });
    };

    $.fn.faceTitle.Constructor = FaceTitle;

    /* DATA-API
     * ============== */
    $(function () {
        $(document)
            .on('click.mutabra.data-api', '[data-toggle=face]', function () {
                var $this = $(this);
                var $selected = $this.closest('[data-toggle=face]') || $this;

                $selected.faceGenerator('show');
            })
            .on('change.mutabra.data-api', '[data-toggle=face-title]', function () {
                $(this).faceTitle('refresh');
            })
    });

}(window.jQuery);