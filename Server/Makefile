AXIS_USABLE_LIBS = UCLIBC GLIBC
include $(AXIS_TOP_DIR)/tools/build/rules/common.mak

PROGS     = Server_C_ass2
OBJS1	= Server_C_ass2.c

CFLAGS += -Wall -g -O2
ifeq ($(AXIS_BUILDTYPE),host)
LDFLAGS += -lcapturehost -ljpeg
else
LDFLAGS += -lcapture
endif # AXIS_BUILDTYPE == host

OBJS  = Server_C_ass2.o
all:	$(PROGS)

$(PROGS): $(OBJS)
	$(CC) $(LDFLAGS) $^ $(LIBS) $(LDLIBS) -o $@

clean:
	rm -f $(PROGS) *.o core
	rm -f *.tar

