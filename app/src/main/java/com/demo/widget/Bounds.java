package com.demo.widget;

public class Bounds {

        private int index;
        private int top;
        private int height;
        private int targetTop;
        private int currentTop;
        private int lastCurrentTop;

        public Bounds(int index, int bottom) {
            this.index = index;
            this.top = bottom;
        }

        public Bounds(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getTop() {
            return top;
        }

        public Bounds setTop(int top) {
            this.top = top;
            return this;
        }

        public int getTargetTop() {
            return targetTop;
        }

        public void setTargetTop(int targetTop) {
            this.targetTop = targetTop;
        }

        public int getHeight() {
            return height;
        }

        public Bounds setHeight(int height) {
            this.height = height;
            return this;
        }

    public int getCurrentTop() {
        return currentTop;
    }

    public void setCurrentTop(int currentTop) {
        this.currentTop = currentTop;
    }

    public int getLastCurrentTop() {
        return lastCurrentTop;
    }

    public void setLastCurrentTop(int lastCurrentTop) {
        this.lastCurrentTop = lastCurrentTop;
    }
}
