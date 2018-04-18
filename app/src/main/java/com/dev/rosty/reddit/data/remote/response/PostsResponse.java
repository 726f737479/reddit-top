package com.dev.rosty.reddit.data.remote.response;

import java.util.List;

public class PostsResponse {

    private Holder data;

    public List<Children> getPosts() {

        return data != null ? data.children :  null;
    }

    class Holder {

        private List<Children> children;
    }


    public class Children {

        private Data data;
        private String Kind;

        public Data getData() {
            return data;
        }
    }

    public class Data {

        private String author;
        private String title;
        private String name;
        private long created;
        private int num_comments;
        private String id;
        private String thumbnail;
        private Preview preview;

        public String getAuthor() {
            return author;
        }

        public long getCreated() {
            return created;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public Preview getPreview() {
            return preview;
        }

        public String getTitle() {
            return title;
        }

        public int getNumComments() {
            return num_comments;
        }

        public String getUrl() {

            if (preview != null) {

                if (preview.images != null && preview.images.size() > 0) {

                    if (preview.images.get(0) != null) {

                        if (preview.images.get(0).source != null) {

                            return preview.images.get(0).source.url;
                        }
                    }
                }
            }
            return null;
        }

        class Preview {
            private List<Image> images;
        }

        class Image {
            private Source source;
        }

        class Source {
            private String url;
        }
    }
}


